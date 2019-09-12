package edu.tamu.scholars.middleware.graphql.generator;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.getDiscoveryDocumentTypes;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.FileSystemUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import edu.tamu.scholars.middleware.discovery.annotation.NestedMultiValuedProperty;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.Common;
import edu.tamu.scholars.middleware.graphql.config.model.Composite;
import edu.tamu.scholars.middleware.graphql.config.model.CompositeReference;
import io.leangen.graphql.annotations.types.GraphQLInterface;
import io.leangen.graphql.annotations.types.GraphQLType;

public class NestedDocumentGenerator {

    private static final List<String> SINGULAR_WORDS_ENDING_WITH_S = Arrays.asList(new String[] { "GeographicFocus", "SameAs", "AwardsAndHonors" });

    private static final String DISCOVERY_ABSTRACT_NESTED_DOCUMENT_CLASS_NAME = "AbstractNestedDocument";

    private static final ClassName LIST = ClassName.get("java.util", "List");

    private static final ClassName STRING = ClassName.get("java.lang", "String");

    private final String destinationPath;

    private final String destinationPackage;

    private final Optional<String> compositesPath;

    private final List<Composite> composites;

    public NestedDocumentGenerator(String destinationPath, String destinationPackage) {
        this.destinationPath = destinationPath;
        this.destinationPackage = destinationPackage;
        this.compositesPath = Optional.empty();
        this.composites = new ArrayList<Composite>();
    }

    public NestedDocumentGenerator(String destinationPath, String destinationPackage, String compositesPath) {
        this.destinationPath = destinationPath;
        this.destinationPackage = destinationPackage;
        this.compositesPath = Optional.of(compositesPath);
        this.composites = new ArrayList<Composite>();
    }

    public void generate() throws JsonParseException, JsonMappingException, IOException {
        FileSystemUtils.deleteRecursively(new File(String.format("%s%s%s", destinationPath, File.separator, destinationPackage.replace(".", "/"))));

        if (compositesPath.isPresent()) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            Path path = Paths.get(compositesPath.get());
            URL url = path.toUri().toURL();
            // @formatter:off
            composites.addAll(mapper.readValue(url, new TypeReference<List<Composite>>() {}));
            // @formatter:on
        }

        buildAbstractNestedDocument();

        for (Class<?> docType : getDiscoveryDocumentTypes()) {
            buildNestedDocument(docType);
        }
    }

    private void buildAbstractNestedDocument() {
        String packagePath = destinationPackage;
        String className = DISCOVERY_ABSTRACT_NESTED_DOCUMENT_CLASS_NAME;

        // @formatter:off
        TypeSpec abstractNestedDocumentClass = builder(className, Modifier.PUBLIC, Modifier.ABSTRACT)
            .addSuperinterface(Serializable.class)
            .superclass(AbstractSolrDocument.class)
            .addAnnotation(AnnotationSpec.builder(GraphQLInterface.class)
                .addMember("name", "$S", "AbstractNestedDocument")
                .addMember("implementationAutoDiscovery", "$L", true)
                .build())
            .addField(serializeVersionUID(String.format("%s.%s", packagePath, className)))
            .addMethod(constructor())
            .build();
        // @formatter:on

        JavaFile nestedDocumentFile = JavaFile.builder(packagePath, abstractNestedDocumentClass).build();

        File sourceDirectory = new File(destinationPath);

        try {
            nestedDocumentFile.writeTo(sourceDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildNestedDocument(Class<?> docType) {
        String packagePath = destinationPackage;

        String nestedPackagePath = String.format("%s.%s", destinationPackage, docType.getSimpleName().toLowerCase());

        String commonPackagePath = String.format("%s.%s", destinationPackage, "common");

        String className = docType.getSimpleName();

        Optional<Composite> composite = composites.stream().filter(c -> c.getType().equals(className)).findAny();
        // @formatter:off
        List<Field> basicFields = FieldUtils.getAllFieldsList(docType)
            .stream()
            .filter(field -> !field.getName().equals("id"))
            .filter(field -> !field.getName().equals("syncIds"))
            .filter(field -> !field.getName().startsWith("$"))
            .collect(Collectors.toList());

        List<Field> nestedObjectFields = FieldUtils.getFieldsListWithAnnotation(docType, NestedObject.class)
            .stream()
            .filter(field -> !field.getName().equals("id"))
            .filter(field -> !field.getName().equals("syncIds"))
            .filter(field -> !field.getName().startsWith("$"))
            .collect(Collectors.toList());
        // @formatter:on

        List<FieldSpec> fields = new ArrayList<FieldSpec>();
        List<MethodSpec> methods = new ArrayList<MethodSpec>();

        List<String> imports = new ArrayList<String>();

        for (Field field : nestedObjectFields) {
            NestedObject nestedObject = field.getAnnotation(NestedObject.class);

            Optional<CompositeReference> compositeReference = Optional.empty();

            if (composite.isPresent()) {
                compositeReference = composite.get().getReferences().stream().filter(r -> r.getName().equals(field.getName())).findAny();
            }

            if (compositeReference.isPresent()) {

                imports.add(String.format("%s.%s", packagePath, compositeReference.get().getType()));

                ClassName nestedClass = ClassName.get(packagePath, compositeReference.get().getType());

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
            } else {
                if (nestedObject.root()) {
                    String nestedClassName = buildNestedClass(field, field.getName());

                    imports.add(String.format("%s.%s", field.getDeclaringClass().equals(Common.class) ? commonPackagePath : nestedPackagePath, nestedClassName));

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
            }

            for (Reference reference : nestedObject.properties()) {
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
                fieldBuilder.addAnnotation(AnnotationSpec.builder(JsonProperty.class).addMember("value", "$S", jsonProperty.get().value()).build());
            }

            fields.add(fieldBuilder.build());
            methods.add(getter(type, field.getName()));
            methods.add(setter(type, field.getName()));
        }

        // @formatter:off
        Builder builder = builder(className, Modifier.PUBLIC)
            .addAnnotation(AnnotationSpec.builder(GraphQLType.class)
                .addMember("name", "$S", className)
                .build())
            .addAnnotation(AnnotationSpec.builder(JsonInclude.class)
                .addMember("value", "$L", NON_EMPTY)
                .build())
            .addField(serializeVersionUID(String.format("%s.%s", packagePath, className)));
        // @formatter:on

        fields.forEach(f -> builder.addField(f));

        builder.addMethod(constructor());

        methods.forEach(m -> builder.addMethod(m));

        createFile(builder, packagePath, className, imports);

        // TODO: if and when property types are imported, remove imports list and use this
        // createFile(builder, packagePath);
    }

    private String buildNestedClass(Field field, String baseName) {
        Class<?> docType = field.getDeclaringClass();

        String packagePath = String.format("%s.%s", destinationPackage, docType.getSimpleName().toLowerCase());

        String className = buildClassName(baseName);

        List<FieldSpec> fields = new ArrayList<FieldSpec>();
        List<MethodSpec> methods = new ArrayList<MethodSpec>();

        List<String> imports = new ArrayList<String>();

        Optional<NestedObject> parentNestedObject = Optional.ofNullable(field.getAnnotation(NestedObject.class));

        if (parentNestedObject.isPresent()) {
            fields.add(field(STRING, parentNestedObject.get().label(), Modifier.PRIVATE));
            methods.add(getter(STRING, parentNestedObject.get().label()));
            methods.add(setter(STRING, parentNestedObject.get().label()));

            for (Reference reference : parentNestedObject.get().properties()) {
                String fieldName = reference.key();

                Field nestedField = FieldUtils.getField(docType, reference.value(), true);

                Optional<NestedObject> childNestedObject = Optional.ofNullable(nestedField.getAnnotation(NestedObject.class));

                Optional<NestedMultiValuedProperty> nestedMultiValuedProperty = Optional.ofNullable(nestedField.getAnnotation(NestedMultiValuedProperty.class));

                if (childNestedObject.isPresent()) {
                    String nestedClassName = buildNestedClass(nestedField, reference.value());

                    imports.add(String.format("%s.%s", packagePath, nestedClassName));

                    ClassName nestedClass = ClassName.get(packagePath, nestedClassName);

                    if (nestedMultiValuedProperty.isPresent()) {
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
                    if (nestedMultiValuedProperty.isPresent()) {
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

        // @formatter:off
        Builder builder = builder(className, Modifier.PUBLIC)
            .addAnnotation(AnnotationSpec.builder(GraphQLType.class)
                .addMember("name", "$S", String.format("%s%s", docType.getSimpleName(), className))
                .build())
            .addAnnotation(AnnotationSpec.builder(JsonInclude.class)
                .addMember("value", "$L", NON_EMPTY)
                .build())
            .addField(serializeVersionUID(String.format("%s.%s", packagePath, className)));
        // @formatter:on
        fields.forEach(f -> builder.addField(f));

        builder.addMethod(constructor());

        methods.forEach(m -> builder.addMethod(m));

        createFile(builder, packagePath, className, imports);

        // TODO: if and when property types are imported, remove imports list and use this
        // createFile(builder, packagePath);

        return className;
    }

    private FieldSpec serializeVersionUID(String fullClassName) {
        // @formatter:off
        return FieldSpec
            .builder(long.class, "serialVersionUID", Modifier.PRIVATE, Modifier.STATIC,Modifier.FINAL)
            .initializer("$LL", Long.valueOf(fullClassName.hashCode()))
            .build();
        // @formatter:on
    }

    // TODO: if and when property types are imported, remove this method
    // NOTE: hack to manually add reference imports to allow top level nested documents in parent package
    private void createFile(Builder builder, String packagePath, String className, List<String> imports) {
        ClassName abstractNestedDocumentClass = ClassName.get(destinationPackage, DISCOVERY_ABSTRACT_NESTED_DOCUMENT_CLASS_NAME);

        TypeSpec nestedDocumentClass = builder.superclass(abstractNestedDocumentClass).build();

        // @formatter:off
        JavaFile nestedDocumentFile = JavaFile.builder(packagePath, nestedDocumentClass)
            .addStaticImport(Include.class, "NON_EMPTY")
            .build();
        // @formatter:on

        String[] lines = nestedDocumentFile.toString().split("\\r?\\n");

        StringBuilder fileAsString = new StringBuilder();

        for (int l = 0; l < lines.length; l++) {
            fileAsString.append(String.format("%s\n", lines[l]));
            if (l == 0 && imports.size() > 0) {
                fileAsString.append("\n");
                imports.forEach(i -> {
                    fileAsString.append(String.format("import %s;\n", i));
                });
            }
        }

        String directoryPath = String.format("%s%s%s", destinationPath, File.separator, packagePath.replace(".", File.separator));

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
    private void createFile(Builder builder, String packagePath) {
        ClassName abstractNestedDocumentClass = ClassName.get(destinationPackage, DISCOVERY_ABSTRACT_NESTED_DOCUMENT_CLASS_NAME);

        TypeSpec nestedDocumentClass = builder.superclass(abstractNestedDocumentClass).build();

        JavaFile nestedDocumentFile = JavaFile.builder(packagePath, nestedDocumentClass).build();

        File sourceDirectory = new File(destinationPath);

        try {
            nestedDocumentFile.writeTo(sourceDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // NOTE: not good, but makes for friendlier class names
    private String buildClassName(String baseName) {
        String className = String.format("%s%s", baseName.substring(0, 1).toUpperCase(), baseName.substring(1));
        if (className.endsWith("ies")) {
            className = String.format("%sy", className.substring(0, className.length() - 3));
        } else if (className.endsWith("s") && !SINGULAR_WORDS_ENDING_WITH_S.contains(className)) {
            className = className.substring(0, className.length() - 1);
        }
        return className;
    }

    private Builder builder(String className, Modifier... modifiers) {
        // @formatter:off
        return TypeSpec.classBuilder(className)
            .addJavadoc("This file is automatically generated on compile.\n\nDo not modify this file -- YOUR CHANGES WILL BE ERASED!\n")
            .addModifiers(modifiers);
        // @formatter:on
    }

    private FieldSpec field(TypeName type, String name, Modifier... modifiers) {
        return FieldSpec.builder(type, name, modifiers).build();
    }

    private MethodSpec constructor() {
        // @formatter:off
        return MethodSpec.constructorBuilder()
            .addStatement("super()")
            .addModifiers(Modifier.PUBLIC)
            .build();
        // @formatter:on
    }

    private MethodSpec getter(TypeName type, String name) {
        // @formatter:off
        return MethodSpec.methodBuilder(String.format("get%s%s", name.substring(0, 1).toUpperCase(), name.substring(1)))
            .addModifiers(Modifier.PUBLIC)
            .returns(type)
            .addStatement("return $N", name)
            .build();
        // @formatter:on
    }

    private MethodSpec setter(TypeName type, String name) {
        // @formatter:off
        return MethodSpec.methodBuilder(String.format("set%s%s", name.substring(0, 1).toUpperCase(), name.substring(1)))
            .addModifiers(Modifier.PUBLIC)
            .returns(void.class)
            .addParameter(type, name)
            .addStatement("this.$N = $N", name, name)
            .build();
        // @formatter:on
    }

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            throw new RuntimeException("Please provide either two arguments, destination path and generated model destination package, or three with optional composites config path.");
        }
        try {
            if (args.length == 2) {
                new NestedDocumentGenerator(args[0], args[1]).generate();
            } else {
                new NestedDocumentGenerator(args[0], args[1], args[2]).generate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
