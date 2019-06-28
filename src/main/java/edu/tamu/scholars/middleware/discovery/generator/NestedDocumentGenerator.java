package edu.tamu.scholars.middleware.discovery.generator;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.FileSystemUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.NestedMultiValuedProperty;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;

public class NestedDocumentGenerator {

    private static final List<String> SINGULAR_WORDS_ENDING_WITH_S = Arrays.asList(new String[] {
        "GeographicFocus",
        "SameAs",
        "AwardsAndHonors"
    });
    
    private static final String DISCOVERY_ABSTRACT_NESTED_DOCUMENT_CLASS_NAME = "AbstractNestedDocument";

    private static final String DISCOVERY_MODEL_PACKAGE_PATH = "edu.tamu.scholars.middleware.discovery.model";

    public static final String DISCOVERY_GENERATED_MODEL_PACKAGE_PATH = String.format("%s.generated", DISCOVERY_MODEL_PACKAGE_PATH);

    private static final ClassName LIST = ClassName.get("java.util", "List");

    private static final ClassName STRING = ClassName.get("java.lang", "String");

    public static void main(String[] args) {
        generate();
    }

    public static void generate() {
        FileSystemUtils.deleteRecursively(new File(String.format("src/main/java/%s", DISCOVERY_GENERATED_MODEL_PACKAGE_PATH.replace(".", "/"))));
        buildAbstractNestedDocument();
        for (Class<?> docType : getIndexDocuments()) {
            buildNestedDocumentClass(docType);
        }
    }

    private static Set<Class<?>> getIndexDocuments() {
        Set<Class<?>> documents = new HashSet<Class<?>>();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(CollectionSource.class));
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(DISCOVERY_MODEL_PACKAGE_PATH);
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                documents.add(Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to find class for " + beanDefinition.getBeanClassName(), e);
            }
        }
        return documents;
    }

    private static void buildAbstractNestedDocument() {
        String packagePath = DISCOVERY_GENERATED_MODEL_PACKAGE_PATH;
        String className = DISCOVERY_ABSTRACT_NESTED_DOCUMENT_CLASS_NAME;

        // NOTE: currently the semantic identifier, i.e name, title, etc, of a nested object is label
        // TODO: make the semantic identifier configurable using @NestedObject annotation

        TypeSpec abstractNestedDocumentClass = builder(className, Modifier.PUBLIC, Modifier.ABSTRACT)
                .addSuperinterface(Serializable.class)
                .superclass(AbstractSolrDocument.class)
                .addField(serializeVersionUID(String.format("%s.%s", packagePath, className)))
                .addMethod(constructor())
                .addField(STRING, "label", Modifier.PRIVATE)
                .addMethod(getter(STRING, "label"))
                .addMethod(setter(STRING, "label"))
                .build();

        JavaFile nestedDocumentFile = JavaFile.builder(packagePath, abstractNestedDocumentClass).build();

        File sourceDirectory = new File("src/main/java");

        try {
            nestedDocumentFile.writeTo(sourceDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildNestedDocumentClass(Class<?> docType) {
        String packagePath = DISCOVERY_GENERATED_MODEL_PACKAGE_PATH;

        String nestedPackagePath = String.format("%s.%s", DISCOVERY_GENERATED_MODEL_PACKAGE_PATH, docType.getSimpleName().toLowerCase());

        String className = docType.getSimpleName();

        List<Field> basicFields = FieldUtils.getAllFieldsList(docType)
                .stream()
                .filter(field -> !field.getName().equals("id"))
                .filter(field -> !field.getName().equals("syncIds"))
                .collect(Collectors.toList());

        List<Field> nestedObjectFields = FieldUtils.getFieldsListWithAnnotation(docType, NestedObject.class)
                .stream()
                .filter(field -> !field.getName().equals("id"))
                .filter(field -> !field.getName().equals("syncIds"))
                .collect(Collectors.toList());

        List<FieldSpec> fields = new ArrayList<FieldSpec>();
        List<MethodSpec> methods = new ArrayList<MethodSpec>();
        
        List<String> imports = new ArrayList<String>();

        for(Field field : nestedObjectFields) {
            NestedObject nestedObject = field.getAnnotation(NestedObject.class);

            if (nestedObject.root()) {
                String nestedClassName = buildNestedClass(field, field.getName());
                
                imports.add(String.format("%s.%s", nestedPackagePath, nestedClassName));

                ClassName nestedClass = ClassName.get(packagePath, nestedClassName);

                if (List.class.isAssignableFrom(field.getType())) {
                    TypeName listOfNestedClass = ParameterizedTypeName.get(LIST, nestedClass);

                    fields.add(field(listOfNestedClass, field.getName(), Modifier.PRIVATE));
                    methods.add(getter(listOfNestedClass, field.getName()));
                    methods.add(setter(listOfNestedClass, field.getName()));
                } else {
                    
                    fields.add(field(nestedClass, field.getName(), Modifier.PRIVATE));
                    methods.add(getter(nestedClass, field.getName()));
                    methods.add(setter(nestedClass, field.getName()));
                }
            }

            for (Reference reference : nestedObject.value()) {
                Field nestedField = FieldUtils.getField(docType, reference.value(), true);
                basicFields = basicFields.stream().filter(f -> !f.getName().equals(nestedField.getName())).collect(Collectors.toList());
            }

            basicFields = basicFields.stream().filter(f -> !f.getName().equals(field.getName())).collect(Collectors.toList());
        }

        for (Field field : basicFields) {
            TypeName type = TypeName.get(field.getGenericType());
            FieldSpec.Builder fieldBuilder = FieldSpec.builder(type, field.getName(), Modifier.PRIVATE);

            Optional<JsonProperty> jsonProperty = Optional.ofNullable(field.getAnnotation(JsonProperty.class));
            if (jsonProperty.isPresent()) {
                AnnotationSpec annotation = AnnotationSpec.builder(JsonProperty.class).addMember("value", "$S", jsonProperty.get().value()).build();
                fieldBuilder.addAnnotation(annotation);
            }
            
            fields.add(fieldBuilder.build());
            methods.add(getter(type, field.getName()));
            methods.add(setter(type, field.getName()));
        }
        
        Builder builder = builder(className, Modifier.PUBLIC)
                .addField(serializeVersionUID(String.format("%s.%s", packagePath, className)));

        fields.forEach(f -> builder.addField(f));

        builder.addMethod(constructor());
        
        methods.forEach(m -> builder.addMethod(m));

        createFile(builder, packagePath, className, imports);

        // TODO: if and when property types are imported, remove imports list and use this
        // createFile(builder, packagePath);
    }

    private static String buildNestedClass(Field field, String baseName) {
        Class<?> docType = field.getDeclaringClass();

        String packagePath = String.format("%s.%s", DISCOVERY_GENERATED_MODEL_PACKAGE_PATH, docType.getSimpleName().toLowerCase());

        String className = buildClassName(baseName);

        List<FieldSpec> fields = new ArrayList<FieldSpec>();
        List<MethodSpec> methods = new ArrayList<MethodSpec>();

        List<String> imports = new ArrayList<String>();

        Optional<NestedObject> parentNestedObject = Optional.ofNullable(field.getAnnotation(NestedObject.class));

        if (parentNestedObject.isPresent()) {
            for (Reference reference : parentNestedObject.get().value()) {
                String fieldName = reference.key();
    
                Field nestedField = FieldUtils.getField(docType, reference.value(), true);
                
                Optional<NestedObject> childNestedObject = Optional.ofNullable(nestedField.getAnnotation(NestedObject.class));
    
                Optional<NestedMultiValuedProperty> nestedMultiValuedProperty = Optional.ofNullable(nestedField.getAnnotation(NestedMultiValuedProperty.class));

                if(childNestedObject.isPresent()) {
                    String nestedClassName = buildNestedClass(nestedField, reference.value());
                    
                    imports.add(String.format("%s.%s", packagePath, nestedClassName));
    
                    ClassName nestedClass = ClassName.get(packagePath, nestedClassName);
    
                    if(nestedMultiValuedProperty.isPresent()) {
                        TypeName listOfNestedClass = ParameterizedTypeName.get(LIST, nestedClass);
                        fields.add(field(listOfNestedClass, fieldName, Modifier.PRIVATE));
                        methods.add(getter(listOfNestedClass, fieldName));
                        methods.add(setter(listOfNestedClass, fieldName));
                    } else {
                        fields.add(field(nestedClass, fieldName, Modifier.PRIVATE));
                        methods.add(getter(nestedClass, fieldName));
                        methods.add(setter(nestedClass, fieldName));
                    }
                } else {
                    if(nestedMultiValuedProperty.isPresent()) {
                        TypeName listOfStrings = ParameterizedTypeName.get(LIST, STRING);
                        fields.add(field(listOfStrings, fieldName, Modifier.PRIVATE));
                        methods.add(getter(listOfStrings, fieldName));
                        methods.add(setter(listOfStrings, fieldName));
                    } else {
                        fields.add(field(STRING, fieldName, Modifier.PRIVATE));
                        methods.add(getter(STRING, fieldName));
                        methods.add(setter(STRING, fieldName));
                    }
                }
            }
        }

        Builder builder = builder(className, Modifier.PUBLIC)
                .addField(serializeVersionUID(String.format("%s.%s", packagePath, className)));

        fields.forEach(f -> builder.addField(f));

        builder.addMethod(constructor());

        methods.forEach(m -> builder.addMethod(m));

        createFile(builder, packagePath, className, imports);

        // TODO: if and when property types are imported, remove imports list and use this
        // createFile(builder, packagePath);

        return className;
    }

    private static FieldSpec serializeVersionUID(String fullClassName) {
        return FieldSpec
                .builder(long.class, "serialVersionUID", Modifier.PRIVATE, Modifier.STATIC,Modifier.FINAL)
                .initializer("$LL", Long.valueOf(fullClassName.hashCode()))
                .build();
    }

    // TODO: if and when property types are imported, remove this method
    // NOTE: hack to manually add reference imports to allow top level nested documents in parent package
    private static void createFile(Builder builder, String packagePath, String className, List<String> imports) {
        ClassName abstractNestedDocumentClass = ClassName.get(DISCOVERY_GENERATED_MODEL_PACKAGE_PATH, DISCOVERY_ABSTRACT_NESTED_DOCUMENT_CLASS_NAME);

        TypeSpec nestedDocumentClass = builder.superclass(abstractNestedDocumentClass).build();

        JavaFile nestedDocumentFile = JavaFile.builder(packagePath, nestedDocumentClass).build();
        
        String[] lines = nestedDocumentFile.toString().split("\\r?\\n");
        
        StringBuilder fileAsString = new StringBuilder();
        
        for (int l = 0; l < lines.length; l++) {
            fileAsString.append(String.format("%s\n", lines[l]));
            if(l == 0 && imports.size() > 0) {
                fileAsString.append("\n");
                imports.forEach(i -> {
                    fileAsString.append(String.format("import %s;\n", i));
                });
            }
        }

        String directoryPath = String.format("%s%s%s", "src/main/java", File.separator, packagePath.replace(".", File.separator));

        File directory = new File(directoryPath);

        directory.mkdirs();

        String filePath = String.format("%s%s%s.java", directoryPath, File.separator, className);

        try {
            Files.write(Paths.get(filePath), fileAsString.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: figure out why reference imports not working, tried $T
    // if and when property types import properly, use this method
    // https://github.com/square/javapoet#t-for-types
    @SuppressWarnings("unused")
    private static void createFile(Builder builder, String packagePath) {
        ClassName abstractNestedDocumentClass = ClassName.get(DISCOVERY_GENERATED_MODEL_PACKAGE_PATH, DISCOVERY_ABSTRACT_NESTED_DOCUMENT_CLASS_NAME);

        TypeSpec nestedDocumentClass = builder.superclass(abstractNestedDocumentClass).build();

        JavaFile nestedDocumentFile = JavaFile.builder(packagePath, nestedDocumentClass).build();

        File sourceDirectory = new File("src/main/java");

        try {
             nestedDocumentFile.writeTo(sourceDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // NOTE: not good, but makes for friendlier class names
    private static String buildClassName(String baseName) {
        String className = String.format("%s%s", baseName.substring(0, 1).toUpperCase(), baseName.substring(1));
        if (className.endsWith("ies")) {
            className = String.format("%sy", className.substring(0, className.length() - 3));
        } else if (className.endsWith("s") && !SINGULAR_WORDS_ENDING_WITH_S.contains(className)) {
            className = className.substring(0, className.length() - 1);
        }
        return className;
    }
    
    private static Builder builder(String className, Modifier...modifiers) {
        return TypeSpec.classBuilder(className)
                .addJavadoc("This file is automatically generated on compile.\n\nDo not modify this file -- YOUR CHANGES WILL BE ERASED!\n")
                .addModifiers(modifiers);
    }

    private static FieldSpec field(TypeName type, String name, Modifier...modifiers) {
        return FieldSpec.builder(type, name, modifiers).build();
    }

    private static MethodSpec constructor() {
        return MethodSpec
                .constructorBuilder()
                .addStatement("super()")
                .addModifiers(Modifier.PUBLIC)
                .build();
    }

    private static MethodSpec getter(TypeName type, String name) {
        return MethodSpec
                .methodBuilder(String.format("get%s%s", name.substring(0, 1).toUpperCase(), name.substring(1)))
                .addModifiers(Modifier.PUBLIC)
                .returns(type)
                .addStatement("return $N", name)
                .build();
    }

    private static MethodSpec setter(TypeName type, String name) {
        return MethodSpec
                .methodBuilder(String.format("set%s%s", name.substring(0, 1).toUpperCase(), name.substring(1)))
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(type, name)
                .addStatement("this.$N = $N", name, name)
                .build();
    }

}
