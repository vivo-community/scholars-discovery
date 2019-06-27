package edu.tamu.scholars.middleware.discovery.generator;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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

    private static File sourcePath;
    
    private static Random random = new Random();

    public static void main(String[] args) {
        generate();
    }

    public static void generate() {
        
        FileSystemUtils.deleteRecursively(new File(String.format("src/main/java/%s", DISCOVERY_GENERATED_MODEL_PACKAGE_PATH.replace(".", "/"))));
        
        sourcePath = new File("src/main/java");
        
        buildAbstractNestedDocument();
        
        for (Class<?> docType : getIndexDocuments()) {
            buildNestedDocumentClass(docType);
        }
    }
    
    private static void buildAbstractNestedDocument() {
        String packageName = DISCOVERY_GENERATED_MODEL_PACKAGE_PATH;
        String className = DISCOVERY_ABSTRACT_NESTED_DOCUMENT_CLASS_NAME;

        TypeSpec abstractNestedDocumentClass = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addSuperinterface(Serializable.class)
                .superclass(AbstractSolrDocument.class)
                .addField(serializeVersionUID())
                .addField(STRING, "label", Modifier.PRIVATE)
                .addMethod(getter(STRING, "label"))
                .addMethod(setter(STRING, "label"))
                .addMethod(constructor())
                .build();

        JavaFile nestedDocumentFile = JavaFile.builder(packageName, abstractNestedDocumentClass).build();

        try {
            nestedDocumentFile.writeTo(sourcePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildNestedDocumentClass(Class<?> docType) {
        String packagePath = String.format("%s.%s", DISCOVERY_GENERATED_MODEL_PACKAGE_PATH, docType.getSimpleName().toLowerCase());
        
        String className = docType.getSimpleName();

        Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addField(serializeVersionUID());

        List<Field> fields = FieldUtils.getAllFieldsList(docType)
                .stream()
                .filter(field -> !field.getName().equals("id"))
                .filter(field -> !field.getName().equals("syncIds"))
                .collect(Collectors.toList());
        
        List<Field> nestedObjectFields = FieldUtils.getFieldsListWithAnnotation(docType, NestedObject.class)
                .stream()
                .filter(field -> !field.getName().equals("id"))
                .filter(field -> !field.getName().equals("syncIds"))
                .collect(Collectors.toList());

        for(Field field : nestedObjectFields) {
            NestedObject nestedObject = field.getAnnotation(NestedObject.class);

            if (nestedObject.root()) {
                String nestedClassName = buildNestedClass(field, field.getName());

                ClassName nestedClass = ClassName.get(packagePath, nestedClassName);

                if (List.class.isAssignableFrom(field.getType())) {
                    TypeName listOfNestedClass = ParameterizedTypeName.get(LIST, nestedClass);

                    builder
                        .addField(listOfNestedClass, field.getName(), Modifier.PRIVATE)
                        .addMethod(getter(listOfNestedClass, field.getName()))
                        .addMethod(setter(listOfNestedClass, field.getName()));
                } else {
                    builder
                        .addField(nestedClass, field.getName(), Modifier.PRIVATE)
                        .addMethod(getter(nestedClass, field.getName()))
                        .addMethod(setter(nestedClass, field.getName()));
                }

            }

            for (Reference reference : nestedObject.value()) {
                Field nestedField = FieldUtils.getField(docType, reference.value(), true);
                
                fields = fields.stream().filter(f -> !f.getName().equals(nestedField.getName())).collect(Collectors.toList());
            }

            fields = fields.stream().filter(f -> !f.getName().equals(field.getName())).collect(Collectors.toList());
        }

        for (Field field : fields) {
            TypeName type = TypeName.get(field.getGenericType());
            FieldSpec.Builder fieldBuilder = FieldSpec.builder(type, field.getName(), Modifier.PRIVATE);

            Optional<JsonProperty> jsonProperty = Optional.ofNullable(field.getAnnotation(JsonProperty.class));
            if (jsonProperty.isPresent()) {
                AnnotationSpec annotation = AnnotationSpec.builder(JsonProperty.class).addMember("value", "$S", jsonProperty.get().value()).build();
                fieldBuilder.addAnnotation(annotation);
            }

            builder
                .addField(fieldBuilder.build())
                .addMethod(getter(type, field.getName()))
                .addMethod(setter(type, field.getName()));
        }

        createFile(builder, packagePath);
    }

    private static String buildNestedClass(Field field, String baseName) {
        Class<?> docType = field.getDeclaringClass();

        String packagePath = String.format("%s.%s", DISCOVERY_GENERATED_MODEL_PACKAGE_PATH, docType.getSimpleName().toLowerCase());

        String className = buildClassName(baseName);

        Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addField(serializeVersionUID());

        Optional<NestedObject> parentNestedObject = Optional.ofNullable(field.getAnnotation(NestedObject.class));

        if (parentNestedObject.isPresent()) {
            for (Reference reference : parentNestedObject.get().value()) {
                String fieldName = reference.key();
    
                Field nestedField = FieldUtils.getField(docType, reference.value(), true);
                
                Optional<NestedObject> childNestedObject = Optional.ofNullable(nestedField.getAnnotation(NestedObject.class));
    
                Optional<NestedMultiValuedProperty> nestedMultiValuedProperty = Optional.ofNullable(nestedField.getAnnotation(NestedMultiValuedProperty.class));

                if(childNestedObject.isPresent()) {
                    String nestedClassName = buildNestedClass(nestedField, reference.value());
    
                    ClassName nestedClass = ClassName.get(packagePath, nestedClassName);
    
                    if(nestedMultiValuedProperty.isPresent()) {
                        TypeName listOfNestedClass = ParameterizedTypeName.get(LIST, nestedClass);
    
                        builder
                            .addField(listOfNestedClass, fieldName, Modifier.PRIVATE)
                            .addMethod(getter(listOfNestedClass, fieldName))
                            .addMethod(setter(listOfNestedClass, fieldName));
                    } else {
                        builder
                            .addField(nestedClass, fieldName, Modifier.PRIVATE)
                            .addMethod(getter(nestedClass, fieldName))
                            .addMethod(setter(nestedClass, fieldName));
                    }
                } else {
                    if(nestedMultiValuedProperty.isPresent()) {
                        TypeName listOfStrings = ParameterizedTypeName.get(LIST, STRING);
                        
                        builder
                            .addField(listOfStrings, fieldName, Modifier.PRIVATE)
                            .addMethod(getter(listOfStrings, fieldName))
                            .addMethod(setter(listOfStrings, fieldName));
                    } else {
                        builder
                            .addField(STRING, fieldName, Modifier.PRIVATE)
                            .addMethod(getter(STRING, fieldName))
                            .addMethod(setter(STRING, fieldName));
                    }
                }
            }
        }

        createFile(builder, packagePath);

        return className;
    }
    
    private static FieldSpec serializeVersionUID() {
        return FieldSpec
                .builder(long.class, "serialVersionUID", Modifier.PRIVATE, Modifier.STATIC,Modifier.FINAL)
                .initializer("$LL", random.nextLong())
                .build();
    }

    private static void createFile(Builder builder, String packageName) {        
        builder.addMethod(constructor());

        ClassName abstractNestedDocumentClass = ClassName.get(DISCOVERY_GENERATED_MODEL_PACKAGE_PATH, DISCOVERY_ABSTRACT_NESTED_DOCUMENT_CLASS_NAME);

        TypeSpec nestedDocumentClass = builder.superclass(abstractNestedDocumentClass).build();

        JavaFile docFile = JavaFile.builder(packageName, nestedDocumentClass).build();

        try {
            docFile.writeTo(sourcePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // NOTE: not cool, but makes for friendlier class names
    private static String buildClassName(String baseName) {
        String className = String.format("%s%s", baseName.substring(0, 1).toUpperCase(), baseName.substring(1));
        if (className.endsWith("ies")) {
            className = String.format("%sy", className.substring(0, className.length() - 3));
        } else if (className.endsWith("s") && !SINGULAR_WORDS_ENDING_WITH_S.contains(className)) {
            className = className.substring(0, className.length() - 1);
        }
        return className;
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

}
