package edu.tamu.scholars.middleware.discovery.service;

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
import java.util.concurrent.ConcurrentLinkedDeque;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.repository.SolrCrudRepository;

import edu.tamu.scholars.middleware.discovery.annotation.CollectionSource;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.service.TemplateService;
import edu.tamu.scholars.middleware.service.Triplestore;

public abstract class AbstractSolrIndexService<D extends AbstractSolrDocument, R extends SolrCrudRepository<D, String>> implements SolrIndexService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String COLLECTION_SPARQL_TEMPLATE = "collection";

    private static final String FORWARD_SLASH = "/";

    private static final String HASH_TAG = "#";

    private static final String NESTED = "nested";

    @Value("${middleware.index.batchSize:10000}")
    public int indexBatchSize;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private Triplestore triplestore;

    @Autowired
    private R repo;

    public void index() {
        CollectionSource source = type().getAnnotation(CollectionSource.class);
        String query = templateService.templateSparql(COLLECTION_SPARQL_TEMPLATE, source.predicate());
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("%s:\n%s", COLLECTION_SPARQL_TEMPLATE, query));
        }
        try (QueryExecution qe = QueryExecutionFactory.create(query, triplestore.getDataset())) {
            Iterator<Triple> triples = qe.execConstructTriples();
            ConcurrentLinkedDeque<D> documents = new ConcurrentLinkedDeque<D>();
            if (triples.hasNext()) {
                Iterable<Triple> tripleIterable = () -> triples;
                Stream<Triple> tripleStream = StreamSupport.stream(tripleIterable.spliterator(), true);
                tripleStream.forEach(triple -> {
                    String subject = triple.getSubject().toString();
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("Indexing %s %s", type().getSimpleName(), subject));
                    }
                    try {
                        documents.add(createDocument(subject));
                    } catch (DataAccessResourceFailureException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                        logger.error(String.format("Unable to index %s: %s", name(), parse(subject)));
                        logger.error(String.format("Error: %s", e.getMessage()));
                        if (logger.isDebugEnabled()) {
                            e.printStackTrace();
                        }
                    } catch (NullPointerException e) {
                        if (logger.isDebugEnabled()) {
                            e.printStackTrace();
                        }
                    }
                    if (documents.size() == indexBatchSize) {
                        batchSave(documents);
                    }
                });
                if (documents.size() > 0) {
                    batchSave(documents);
                }
            } else {
                logger.warn(String.format("No %s found!", name()));
            }
        }
    }

    public void index(String subject) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Indexing %s %s", type().getSimpleName(), subject));
        }
        try {
            repo.save(createDocument(subject));
        } catch (DataAccessResourceFailureException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            logger.error(String.format("Unable to index %s: %s", name(), parse(subject)));
            logger.error(String.format("Error: %s", e.getMessage()));
            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void batchSave(ConcurrentLinkedDeque<D> documents) {
        try {
            repo.saveAll(documents);
        } catch (Exception e1) {
            logger.warn("Failed to batch save. Attempting individually.");
            if (logger.isDebugEnabled()) {
                e1.printStackTrace();
            }
            documents.forEach(document -> {
                try {
                    repo.save(document);
                } catch (Exception e2) {
                    logger.warn(String.format("Failed to save document with id %s", document.getId()));
                    if (logger.isDebugEnabled()) {
                        e2.printStackTrace();
                    }
                }
            });
        }
        documents.clear();
    }

    public String name() {
        return type().getSimpleName();
    }

    private D createDocument(String subject) throws InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException {
        D document = construct();
        Field field = FieldUtils.getField(type(), ID, true);
        field.set(document, parse(subject));
        lookupProperties(document, subject);
        lookupSyncIds(document);
        return document;
    }

    private void lookupProperties(D document, String subject) {
        FieldUtils.getFieldsListWithAnnotation(type(), PropertySource.class).parallelStream().forEach(field -> {
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

    private void populate(D document, Field field, List<String> values) throws IllegalArgumentException, IllegalAccessException {
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
    private void lookupSyncIds(D document) {
        Set<String> syncIds = new HashSet<String>();
        syncIds.add(document.getId());
        FieldUtils.getFieldsListWithAnnotation(type(), Indexed.class).stream().filter(field -> {
            field.setAccessible(true);
            return field.getAnnotation(Indexed.class).type().contains(NESTED);
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

    @SuppressWarnings("unchecked")
    private D construct() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return (D) type().getConstructor().newInstance(new Object[0]);
    }

    private static String parse(String uri) {
        return uri.substring(uri.lastIndexOf(uri.contains(HASH_TAG) ? HASH_TAG : FORWARD_SLASH) + 1);
    }

}