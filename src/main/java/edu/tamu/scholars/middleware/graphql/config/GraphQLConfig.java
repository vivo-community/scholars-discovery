package edu.tamu.scholars.middleware.graphql.config;

import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.findProperty;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.Criteria.OperationKey;
import org.springframework.data.solr.core.query.FacetOptions.FacetSort;

import edu.tamu.scholars.middleware.discovery.argument.Facet;
import edu.tamu.scholars.middleware.discovery.argument.Filter;
import edu.tamu.scholars.middleware.discovery.argument.Index;
import edu.tamu.scholars.middleware.export.argument.Export;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.graphql.ExtendedGeneratorConfiguration;
import io.leangen.graphql.ExtensionProvider;
import io.leangen.graphql.GeneratorConfiguration;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.generator.mapping.ArgumentInjector;
import io.leangen.graphql.generator.mapping.ArgumentInjectorParams;
import io.leangen.graphql.metadata.InputField;
import io.leangen.graphql.metadata.TypedElement;
import io.leangen.graphql.metadata.strategy.query.DefaultOperationBuilder;
import io.leangen.graphql.metadata.strategy.query.DefaultOperationBuilder.TypeInference;
import io.leangen.graphql.metadata.strategy.value.InputFieldBuilder;
import io.leangen.graphql.metadata.strategy.value.InputFieldBuilderParams;

@Configuration
public class GraphQLConfig {

    @Autowired
    private GraphQLSchemaGenerator graphQLSchemaGenerator;

    @PostConstruct
    protected void initGraphQLSchemaGenerator() {
        graphQLSchemaGenerator.withOperationBuilder(new DefaultOperationBuilder(TypeInference.LIMITED));
    }

    @Bean
    public ExtensionProvider<GeneratorConfiguration, ArgumentInjector> argumentInjectorExtensionProvider() {
        return (config, current) -> current.prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                return Index.of(params);
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return Index.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                return Filter.of(params);
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return Filter.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                return Facet.of(params);
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return Facet.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                return Export.of(params);
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return Export.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                return parsePageable(params.getInput(), params.getResolutionEnvironment().fieldType.getChildren().get(0).getName());
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return Pageable.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                return parseSort(params.getInput(), params.getResolutionEnvironment().fieldType.getChildren().get(0).getName());
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return Sort.class.equals(type.getType());
            }

        });
    }

    @Bean
    public ExtensionProvider<ExtendedGeneratorConfiguration, InputFieldBuilder> inputFieldBuilder() {
        return (config, current) -> current.prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("field", "Index field", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("operationKey", "Index operation key", new TypedElement(GenericTypeReflector.annotate(OperationKey.class)), GenericTypeReflector.annotate(OperationKey.class), null));
                fields.add(new InputField("option", "Index option", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return Index.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("field", "Filter field", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("value", "Filter value", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return Filter.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("field", "Facet field", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("sort", "Facet sort", new TypedElement(GenericTypeReflector.annotate(FacetSort.class)), GenericTypeReflector.annotate(FacetSort.class), FacetSort.COUNT));
                fields.add(new InputField("limit", "Facet limit", new TypedElement(GenericTypeReflector.annotate(int.class)), GenericTypeReflector.annotate(int.class), 10));
                fields.add(new InputField("offset", "Facet offset", new TypedElement(GenericTypeReflector.annotate(int.class)), GenericTypeReflector.annotate(int.class), 0));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return Facet.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("field", "Export field", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("label", "Export label", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("delimiter", "Export delimiter", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return Export.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("pageNumber", "Page number", new TypedElement(GenericTypeReflector.annotate(int.class)), GenericTypeReflector.annotate(int.class), 0));
                fields.add(new InputField("pageSize", "Page size", new TypedElement(GenericTypeReflector.annotate(int.class)), GenericTypeReflector.annotate(int.class), 10));
                fields.add(new InputField("sort", "Page sorting", new TypedElement(GenericTypeReflector.annotate(Sort.class)), GenericTypeReflector.annotate(Sort.class), null));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return Pageable.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("orders", "Orders for sorting", new TypedElement(GenericTypeReflector.annotate(Sort.Order[].class)), GenericTypeReflector.annotate(Sort.Order[].class), null));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return Sort.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("property", "Sorting order property", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("direction", "Sorting order direction", new TypedElement(GenericTypeReflector.annotate(Sort.Direction.class)), GenericTypeReflector.annotate(Sort.Direction.class), Sort.Direction.ASC));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return Sort.Order.class.equals(type.getType());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private Pageable parsePageable(Object input, String type) {
        Map<String, Object> page = (Map<String, Object>) input;
        Integer pageNumber = (Integer) page.get("pageNumber");
        Integer pageSize = (Integer) page.get("pageSize");
        return PageRequest.of(pageNumber, pageSize, parseSort(page.get("sort"), type));
    }

    @SuppressWarnings("unchecked")
    private Sort parseSort(Object input, String type) {
        Map<String, Object> sort = (Map<String, Object>) input;
        if (sort != null && sort.containsKey("orders")) {
            List<Sort.Order> orders = new ArrayList<>();
            for (Map<String, Object> order : (List<Map<String, Object>>) sort.get("orders")) {
                Sort.Direction direction = (Sort.Direction) order.get("direction");
                String property = findProperty(type, (String) order.get("property"));
                orders.add(new Sort.Order(direction, property));
            }
            return Sort.by(orders);
        }
        return Sort.unsorted();
    }

}
