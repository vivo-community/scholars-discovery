package edu.tamu.scholars.middleware.discovery.service.jena;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.NESTED_DELIMITER;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.shared.InvalidPropertyURIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.mapping.Indexed;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.service.Harvester;
import edu.tamu.scholars.middleware.service.TemplateService;
import edu.tamu.scholars.middleware.service.Triplestore;
import reactor.core.publisher.Flux;

public class TriplestoreHarvester implements Harvester {

    private static final Logger logger = LoggerFactory.getLogger(TriplestoreHarvester.class);

    private static final String COLLECTION_SPARQL_TEMPLATE = "collection";

    private static final String FORWARD_SLASH = "/";

    private static final String HASH_TAG = "#";

    private static final String NESTED = "nested";

    @Autowired
    private Triplestore triplestore;

    @Autowired
    private TemplateService templateService;

    @Value("${vivo.base-url:http://localhost:8080/vivo}")
    private String vivoUrl;

    private final Class<AbstractIndexDocument> type;

    private final List<TypeOp> propertySourceTypeOps;

    private final List<Field> indexedFields;

    public TriplestoreHarvester(Class<AbstractIndexDocument> type) {
        this.type = type;
        this.propertySourceTypeOps = FieldUtils.getFieldsListWithAnnotation(type, PropertySource.class).stream().map(this::getTypeOp).collect(Collectors.toList());
        this.indexedFields = FieldUtils.getFieldsListWithAnnotation(type, Indexed.class);
    }

    public Flux<AbstractIndexDocument> harvest() {
        CollectionSource source = type.getAnnotation(CollectionSource.class);
        String query = templateService.templateSparql(COLLECTION_SPARQL_TEMPLATE, source.predicate());
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("%s:\n%s", COLLECTION_SPARQL_TEMPLATE, query));
        }
        QueryExecution queryExecution = triplestore.createQueryExecution(query);
        Iterator<Triple> tripleIterator = queryExecution.execConstructTriples();
        Iterable<Triple> triples = () -> tripleIterator;
        // @formatter:off
        return Flux.fromIterable(triples)
            .map(this::subject)
            .map(this::harvest)
            .doFinally(onFinally -> queryExecution.close());
        // @formatter:on
    }

    public AbstractIndexDocument harvest(String subject) {
        try {
            return createDocument(subject);
        } catch (Exception e) {
            logger.error(String.format("Unable to index %s: %s", type.getSimpleName(), parse(subject)));
            logger.error(String.format("Error: %s", e.getMessage()));
            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    public Class<AbstractIndexDocument> type() {
        return type;
    }

    private String subject(Triple triple) {
        return triple.getSubject().toString();
    }

    private AbstractIndexDocument createDocument(String subject) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        AbstractIndexDocument document = construct();
        Field field = FieldUtils.getField(type, ID, true);
        field.set(document, parse(subject));
        lookupProperties(document, subject);
        lookupSyncIds(document);
        return document;
    }

    private void lookupProperties(AbstractIndexDocument document, String subject) {
        propertySourceTypeOps.parallelStream().forEach(typeOp -> {
            try {
                PropertySource source = typeOp.getPropertySource();
                Model model = queryForModel(source, subject);
                List<Object> values = lookupProperty(typeOp, source, model);
                populate(document, typeOp.getField(), values);
            } catch (Exception e) {
                logger.error(String.format("Unable to populate document %s: %s", name(), parse(subject)));
                logger.error(String.format("Error: %s", e.getMessage()));
                if (logger.isDebugEnabled()) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Model queryForModel(PropertySource source, String subject) {
        String query = templateService.templateSparql(source.template(), subject);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("%s:\n%s", source.template(), query));
        }
        try (QueryExecution qe = triplestore.createQueryExecution(query)) {
            Model model = qe.execConstruct();
            if (logger.isDebugEnabled()) {
                model.write(System.out, "RDF/XML");
            }
            return model;
        }
    }

    private List<Object> lookupProperty(TypeOp typeOp, PropertySource source, Model model) {
        List<Object> values = new ArrayList<>();
        ResIterator resources = model.listSubjects();
        while (resources.hasNext()) {
            Resource resource = resources.next();
            values.addAll(queryForProperty(typeOp, source, model, resource));
        }
        return values;
    }

    private List<Object> queryForProperty(TypeOp typeOp, PropertySource source, Model model, Resource resource) {
        List<Object> values = new ArrayList<>();
        StmtIterator statements;
        try {
            statements = resource.listProperties(model.createProperty(source.predicate()));
        } catch (InvalidPropertyURIException exception) {
            logger.error(String.format("%s lookup by %s", typeOp.getField().getName(), source.predicate()));
            throw exception;
        }

        while (statements.hasNext()) {
            Statement statement = statements.next();
            String object = statement.getObject().toString();
            String value = source.parse() ? parse(object) : object;
            value = value.replace("\\\"", "\"");
            if (value.contains("^^")) {
                value = value.substring(0, value.indexOf("^^"));
            }
            if (source.relative()) {
                value = vivoUrl + value;
            }
            if (source.unique() && values.stream().map(v -> v.toString()).anyMatch(value::equalsIgnoreCase)) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("%s has duplicate value %s", typeOp.getField().getName(), value));
                }
            } else {
                if (source.split()) {
                    for (String v : value.split("\\|\\|")) {
                        values.add(typeOp.type(v));
                    }
                } else {
                    values.add(typeOp.type(value));
                }
            }
        }
        return values;
    }

    private void populate(AbstractIndexDocument document, Field field, List<Object> values) throws IllegalArgumentException, IllegalAccessException {
        if (values.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Could not find values for %s", field.getName()));
            }
        } else {
            field.setAccessible(true);
            if (List.class.isAssignableFrom(field.getType())) {
                field.set(document, values);
            } else {
                field.set(document, values.get(0));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void lookupSyncIds(AbstractIndexDocument document) {
        Set<String> syncIds = new HashSet<String>();
        syncIds.add(document.getId());
        indexedFields.stream().filter(this::isNestedField).peek(field -> field.setAccessible(true)).forEach(field -> {
            try {
                Object value = field.get(document);
                if (value != null) {
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        ((Collection<String>) value).forEach(v -> addSyncId(syncIds, v));
                    } else {
                        addSyncId(syncIds, (String) value);
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                logger.error(String.format("Unable to get value of %s %s", name(), field.getName()));
                logger.error(String.format("Error: %s", e.getMessage()));
                if (logger.isDebugEnabled()) {
                    e.printStackTrace();
                }
            }
        });
        document.setSyncIds(syncIds);
    }

    private boolean isNestedField(Field field) {
        return !field.getName().equals(ID) && field.getAnnotation(Indexed.class).type().startsWith(NESTED);
    }

    private void addSyncId(Set<String> syncIds, String value) {
        String[] vParts = value.split(NESTED_DELIMITER);
        for (int i = 1; i < vParts.length; i++) {
            syncIds.add(vParts[i]);
        }
    }

    private AbstractIndexDocument construct() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return type.getConstructor().newInstance(new Object[0]);
    }

    private String name() {
        return type.getSimpleName();
    }

    private String parse(String uri) {
        return uri.substring(uri.lastIndexOf(uri.contains(HASH_TAG) ? HASH_TAG : FORWARD_SLASH) + 1);
    }

    private TypeOp getTypeOp(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Class<?> collectionType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            if (String.class.isAssignableFrom(collectionType)) {
                return new StringOp(field);
            } else if (Integer.class.isAssignableFrom(collectionType)) {
                return new IntegerOp(field);
            } else if (Float.class.isAssignableFrom(collectionType)) {
                return new FloatOp(field);
            } else if (Double.class.isAssignableFrom(collectionType)) {
                return new DoubleOp(field);
            }
        } else if (String.class.isAssignableFrom(field.getType())) {
            return new StringOp(field);
        } else if (Integer.class.isAssignableFrom(field.getType())) {
            return new IntegerOp(field);
        } else if (Float.class.isAssignableFrom(field.getType())) {
            return new FloatOp(field);
        } else if (Double.class.isAssignableFrom(field.getType())) {
            return new DoubleOp(field);
        }
        return new StringOp(field);
    }

    private interface TypeOp {
        public Object type(String value);

        public Field getField();

        public PropertySource getPropertySource();

    }

    private abstract class AbstractTypeOp implements TypeOp {
        private final Field field;

        private final PropertySource source;

        public AbstractTypeOp(Field field) {
            this.field = field;
            this.source = field.getAnnotation(PropertySource.class);
        }

        public Field getField() {
            return field;
        }

        public PropertySource getPropertySource() {
            return source;
        }
    }

    private class StringOp extends AbstractTypeOp {
        public StringOp(Field field) {
            super(field);
        }

        public Object type(String value) {
            return value;
        }
    }

    private class IntegerOp extends AbstractTypeOp {
        public IntegerOp(Field field) {
            super(field);
        }

        public Object type(String value) {
            return Integer.parseInt(value);
        }
    }

    private class FloatOp extends AbstractTypeOp {
        public FloatOp(Field field) {
            super(field);
        }

        public Object type(String value) {
            return Float.parseFloat(value);
        }
    }

    private class DoubleOp extends AbstractTypeOp {
        public DoubleOp(Field field) {
            super(field);
        }

        public Object type(String value) {
            return Double.parseDouble(value);
        }
    }

}
