package edu.tamu.scholars.middleware.discovery.service.jena;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.NESTED_DELIMITER;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.shared.InvalidPropertyURIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.mapping.Indexed;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.service.Harvester;
import edu.tamu.scholars.middleware.service.TemplateService;
import edu.tamu.scholars.middleware.service.Triplestore;

public class LocalTriplestoreHarvester implements Harvester {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String COLLECTION_SPARQL_TEMPLATE = "collection";

    private static final String FORWARD_SLASH = "/";

    private static final String HASH_TAG = "#";

    private static final String NESTED = "nested";

    @Autowired
    private Triplestore triplestore;

    @Autowired
    private TemplateService templateService;

    private final Class<AbstractIndexDocument> type;

    public LocalTriplestoreHarvester(Class<AbstractIndexDocument> type) {
        this.type = type;
    }

    public Stream<AbstractIndexDocument> harvest() {
        CollectionSource source = type.getAnnotation(CollectionSource.class);
        String query = templateService.templateSparql(COLLECTION_SPARQL_TEMPLATE, source.predicate());
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("%s:\n%s", COLLECTION_SPARQL_TEMPLATE, query));
        }
        try (QueryExecution qe = QueryExecutionFactory.create(query, triplestore.getDataset())) {
            Iterator<Triple> triples = qe.execConstructTriples();
            Iterable<Triple> tripleIterable = () -> triples;
            return StreamSupport.stream(tripleIterable.spliterator(), true).map(triple -> harvest(triple.getSubject().toString())).collect(Collectors.toList()).stream();
        }
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

    private AbstractIndexDocument createDocument(String subject) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        AbstractIndexDocument document = construct();
        Field field = FieldUtils.getField(type, ID, true);
        field.set(document, parse(subject));
        lookupProperties(document, subject);
        lookupSyncIds(document);
        return document;
    }

    private void lookupProperties(AbstractIndexDocument document, String subject) {
        FieldUtils.getFieldsListWithAnnotation(document.getClass(), PropertySource.class).parallelStream().forEach(field -> {
            PropertySource source = field.getAnnotation(PropertySource.class);
            Model model = queryForModel(source, subject);
            String property = field.getName();
            List<String> values = lookupProperty(property, source, model);
            try {
                populate(document, field, values);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                logger.error(String.format("Unable to populat document %s: %s", name(), parse(subject)));
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
        try (QueryExecution qe = QueryExecutionFactory.create(query, triplestore.getDataset())) {
            Model model = qe.execConstruct();
            if (logger.isDebugEnabled()) {
                model.write(System.out, "RDF/XML");
            }
            return model;
        }
    }

    private List<String> lookupProperty(String property, PropertySource source, Model model) {
        List<String> values = new ArrayList<String>();
        ResIterator resources = model.listSubjects();
        while (resources.hasNext()) {
            Resource resource = resources.next();
            values.addAll(queryForProperty(property, source, model, resource));
        }
        return values;
    }

    private List<String> queryForProperty(String property, PropertySource source, Model model, Resource resource) {
        List<String> values = new ArrayList<String>();
        StmtIterator statements;
        try {
            statements = resource.listProperties(model.createProperty(source.predicate()));
        } catch (InvalidPropertyURIException exception) {
            logger.error(String.format("%s lookup by %s", property, source.predicate()));
            throw exception;
        }
        while (statements.hasNext()) {
            Statement statement = statements.next();
            String object = statement.getObject().toString();
            String value = source.parse() ? parse(object) : object;
            if (value.contains("^^")) {
                value = value.substring(0, value.indexOf("^^"));
            }
            if (source.unique() && values.stream().anyMatch(value::equalsIgnoreCase)) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("%s has duplicate value %s", property, value));
                }
            } else {
                values.add(value);
            }
        }
        return values;
    }

    private void populate(AbstractIndexDocument document, Field field, List<String> values) throws IllegalArgumentException, IllegalAccessException {
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
        FieldUtils.getFieldsListWithAnnotation(document.getClass(), Indexed.class).stream().filter(field -> {
            field.setAccessible(true);
            return !field.getName().equals(ID) && field.getAnnotation(Indexed.class).type().startsWith(NESTED);
        }).forEach(field -> {
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

}
