package edu.tamu.scholars.middleware.graphql.config;

import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.findProperty;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.FacetOptions.FacetSort;

import edu.tamu.scholars.middleware.discovery.DiscoveryConstants;
import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;
import edu.tamu.scholars.middleware.export.argument.ExportArg;
import edu.tamu.scholars.middleware.model.OpKey;
import edu.tamu.scholars.middleware.view.model.FacetType;
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
                AnnotatedType returnType = params.getResolutionEnvironment().resolver.getReturnType();
                AnnotatedType genericType = GenericTypeReflector.getTypeParameter(returnType, Iterable.class.getTypeParameters()[0]);
                return parseQuery(params.getInput(), genericType.getType().getTypeName());
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return QueryArg.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                return FilterArg.of(params);
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return FilterArg.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                return FacetArg.of(params);
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return FacetArg.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                return BoostArg.of(params);
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return BoostArg.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                AnnotatedType returnType = params.getResolutionEnvironment().resolver.getReturnType();
                AnnotatedType genericType = GenericTypeReflector.getTypeParameter(returnType, Iterable.class.getTypeParameters()[0]);
                return parseHighlight(params.getInput(), genericType.getType().getTypeName());
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return HighlightArg.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                AnnotatedType returnType = params.getResolutionEnvironment().resolver.getReturnType();
                AnnotatedType genericType = GenericTypeReflector.getTypeParameter(returnType, Iterable.class.getTypeParameters()[0]);
                return parsePageable(params.getInput(), genericType.getType().getTypeName());
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return Pageable.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                AnnotatedType returnType = params.getResolutionEnvironment().resolver.getReturnType();
                AnnotatedType genericType = GenericTypeReflector.getTypeParameter(returnType, Iterable.class.getTypeParameters()[0]);
                return parseSort(params.getInput(), genericType.getType().getTypeName());
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return Sort.class.equals(type.getType());
            }

        }).prepend(new ArgumentInjector() {

            @Override
            public Object getArgumentValue(ArgumentInjectorParams params) {
                return ExportArg.of(params);
            }

            @Override
            public boolean supports(AnnotatedType type, Parameter parameter) {
                return ExportArg.class.equals(type.getType());
            }

        });
    }

    @Bean
    public ExtensionProvider<ExtendedGeneratorConfiguration, InputFieldBuilder> inputFieldBuilder() {
        return (config, current) -> current.prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("q", "Query expression", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), DiscoveryConstants.DEFAULT_QUERY));
                fields.add(new InputField("df", "Default search field", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), StringUtils.EMPTY));
                fields.add(new InputField("mm", "Minimum should match", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), StringUtils.EMPTY));
                fields.add(new InputField("qf", "Query fields", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), StringUtils.EMPTY));
                fields.add(new InputField("bq", "Boost query", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), StringUtils.EMPTY));
                fields.add(new InputField("fl", "Fields returned", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), StringUtils.EMPTY));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return QueryArg.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("field", "Filter field", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("value", "Filter value", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("opKey", "Filter operation key", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), OpKey.EQUALS.getKey()));
                fields.add(new InputField("tag", "add SOLR tag to filter (for facet exclusion)", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return FilterArg.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("field", "Facet field", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("sort", "Facet sort", new TypedElement(GenericTypeReflector.annotate(FacetSort.class)), GenericTypeReflector.annotate(FacetSort.class), FacetSort.COUNT));
                fields.add(new InputField("pageSize", "Facet page size", new TypedElement(GenericTypeReflector.annotate(int.class)), GenericTypeReflector.annotate(int.class), 100));
                fields.add(new InputField("pageNumber", "Facet page number", new TypedElement(GenericTypeReflector.annotate(int.class)), GenericTypeReflector.annotate(int.class), 1));
                fields.add(new InputField("type", "Facet type", new TypedElement(GenericTypeReflector.annotate(FacetType.class)), GenericTypeReflector.annotate(FacetType.class), FacetType.STRING));
                fields.add(new InputField("exclusionTag", "Tag (in conjunction with Filter)", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("rangeStart", "Facet range start", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("rangeEnd", "Facet range end", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("rangeGap", "Facet range gap", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return FacetArg.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("field", "Boost field", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("value", "Boost value", new TypedElement(GenericTypeReflector.annotate(Float.class)), GenericTypeReflector.annotate(Float.class), null));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return BoostArg.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("fields", "Highlight fields", new TypedElement(GenericTypeReflector.annotate(String[].class)), GenericTypeReflector.annotate(String[].class), new ArrayList<String>()));
                fields.add(new InputField("prefix", "Highlight simple prefix", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), StringUtils.EMPTY));
                fields.add(new InputField("postfix", "Highlight simple postfix", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), StringUtils.EMPTY));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return HighlightArg.class.equals(type.getType());
            }
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("pageNumber", "Page number", new TypedElement(GenericTypeReflector.annotate(int.class)), GenericTypeReflector.annotate(int.class), 0));
                fields.add(new InputField("pageSize", "Page size", new TypedElement(GenericTypeReflector.annotate(int.class)), GenericTypeReflector.annotate(int.class), 100));
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
        }).prepend(new InputFieldBuilder() {

            @Override
            public Set<InputField> getInputFields(InputFieldBuilderParams params) {
                Set<InputField> fields = new HashSet<>();
                fields.add(new InputField("field", "Export field", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                fields.add(new InputField("label", "Export label", new TypedElement(GenericTypeReflector.annotate(String.class)), GenericTypeReflector.annotate(String.class), null));
                return fields;
            }

            @Override
            public boolean supports(AnnotatedType type) {
                return ExportArg.class.equals(type.getType());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private QueryArg parseQuery(Object input, String type) {
        Map<String, Object> query = (Map<String, Object>) input;
        Optional<String> expression = query.containsKey("q") ? Optional.of((String) query.get("q")) : Optional.of(DiscoveryConstants.DEFAULT_QUERY);
        Optional<String> defaultField = query.containsKey("df") ? Optional.of((String) query.get("df")) : Optional.empty();
        Optional<String> minimumShouldMatch = query.containsKey("mm") ? Optional.of((String) query.get("mm")) : Optional.empty();
        Optional<String> queryField = query.containsKey("qf") ? Optional.of((String) query.get("qf")) : Optional.empty();
        Optional<String> boostQuery = query.containsKey("bq") ? Optional.of((String) query.get("bq")) : Optional.empty();
        Optional<String> fields = query.containsKey("fl") ? Optional.of((String) query.get("fl")) : Optional.empty();
        return QueryArg.of(expression, defaultField, minimumShouldMatch, queryField, boostQuery, fields);
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
                String property = findProperty((String) order.get("property"));
                orders.add(new Sort.Order(direction, property));
            }
            return Sort.by(orders);
        }
        return Sort.unsorted();
    }

    @SuppressWarnings("unchecked")
    private HighlightArg parseHighlight(Object input, String type) {
        Map<String, Object> highlight = (Map<String, Object>) input;
        String[] fields = highlight.containsKey("fields") ? ((List<String>) highlight.get("fields")).toArray(new String[] {}) : new String[] {};
        Optional<String> prefix = highlight.containsKey("prefix") ? Optional.of((String) highlight.get("prefix")) : Optional.empty();
        Optional<String> postfix = highlight.containsKey("postfix") ? Optional.of((String) highlight.get("postfix")) : Optional.empty();
        return HighlightArg.of(fields, prefix, postfix);
    }

}
