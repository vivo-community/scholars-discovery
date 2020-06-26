package edu.tamu.scholars.middleware.graphql.service;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.CLASS;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DEFAULT_QUERY;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetAndHighlightPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryPage;
import edu.tamu.scholars.middleware.graphql.config.model.Composite;
import edu.tamu.scholars.middleware.graphql.config.model.CompositeReference;
import edu.tamu.scholars.middleware.graphql.exception.DocumentNotFoundException;
import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import edu.tamu.scholars.middleware.model.OpKey;
import graphql.language.Field;
import graphql.language.Selection;
import graphql.language.SelectionSet;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@GraphQLApi
public abstract class AbstractNestedDocumentService<ND extends AbstractNestedDocument> implements NestedDocumentService<ND> {

    private final static int MAX_BATCH_SIZE = 500;

    @Autowired
    private IndividualRepo repo;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private List<NestedDocumentService<?>> nestedDocumentServices;

    private final List<Composite> composites = new ArrayList<Composite>();

    @PostConstruct
    public void init() throws JsonParseException, JsonMappingException, IOException {
        Resource compositesResource = new ClassPathResource("graphql/composites.yml");
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        // @formatter:off
        composites.addAll(yamlMapper.readValue(compositesResource.getInputStream(), new TypeReference<List<Composite>>() {}));
        // @formatter:on
    }

    @Override
    public long count() {
        return count(DEFAULT_QUERY, new ArrayList<FilterArg>());
    }

    @Override
    public long count(String query, List<FilterArg> filters) {
        return repo.count(query, augmentFilters(filters));
    }

    @Override
    public boolean existsById(String id) {
        return repo.existsById(id);
    }

    @Override
    public ND getById(String id, List<Field> fields) {
        Optional<ND> document = findById(id, fields);
        if (document.isPresent()) {
            return document.get();
        }
        throw new DocumentNotFoundException(String.format("Could not find %s with id %s", type(), id));
    }

    @Override
    public List<ND> findByIdIn(List<String> ids, List<Field> fields) {
        return repo.findByIdIn(ids).stream().map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public List<ND> findByType(String type, List<Field> fields) {
        return StreamSupport.stream(repo.findByType(type, augmentFilters(new ArrayList<FilterArg>())).spliterator(), false).map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public List<ND> findByType(String type, List<FilterArg> filters, List<Field> fields) {
        return StreamSupport.stream(repo.findByType(type, augmentFilters(filters)).spliterator(), false).map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public List<ND> findMostRecentlyUpdate(Integer limit, List<Field> fields) {
        return findMostRecentlyUpdate(limit, new ArrayList<FilterArg>(), fields);
    }

    @Override
    public List<ND> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters, List<Field> fields) {
        return repo.findMostRecentlyUpdate(limit, augmentFilters(filters)).stream().map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public Iterable<ND> findAll(List<Field> fields) {
        return StreamSupport.stream(repo.findAll(augmentFilters(new ArrayList<FilterArg>())).spliterator(), false).map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public Iterable<ND> findAll(List<FilterArg> filters, List<Field> fields) {
        return StreamSupport.stream(repo.findAll(augmentFilters(filters)).spliterator(), false).map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public Iterable<ND> findAll(Sort sort, List<Field> fields) {
        return StreamSupport.stream(repo.findAll(augmentFilters(new ArrayList<FilterArg>()), sort).spliterator(), false).map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public Iterable<ND> findAll(List<FilterArg> filters, Sort sort, List<Field> fields) {
        return StreamSupport.stream(repo.findAll(augmentFilters(filters), sort).spliterator(), false).map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public DiscoveryPage<ND> findAll(Pageable page, List<Field> fields) {
        return DiscoveryPage.from(repo.findAll(augmentFilters(new ArrayList<FilterArg>()), page).map(document -> toNested(document, fields)));
    }

    @Override
    public DiscoveryPage<ND> findAll(List<FilterArg> filters, Pageable page, List<Field> fields) {
        return DiscoveryPage.from(repo.findAll(augmentFilters(filters), page).map(document -> toNested(document, fields)));
    }

    @Override
    public DiscoveryFacetAndHighlightPage<ND> search(QueryArg query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, HighlightArg highlight, Pageable page, List<Field> fields) {
        FacetAndHighlightPage<Individual> facetPage = repo.search(query, facets, augmentFilters(filters), boosts, highlight, page);

        boolean facetsSelected = selected(fields, "facets");
        boolean highlightsSelected = selected(fields, "highlights");

        // current document cache after serialzed from Individuel
        Map<String, ND> documents = new HashMap<>();

        List<ND> content = facetPage.getContent().stream().map(document -> toNested(document, fields)).peek(document -> {
            // only need document cache for matching highlights
            if (highlightsSelected) {
                documents.put(document.getId(), document);
            }
        }).collect(Collectors.toList());

        Pageable resultsPaging = facetPage.getPageable();
        SolrResultPage<ND> results = new SolrResultPage<ND>(content, resultsPaging, new Long(facetPage.getTotalElements()), null);

        // only process facets if selected in GraphQL query
        if (facetsSelected) {
            Map<org.springframework.data.solr.core.query.Field, Page<FacetFieldEntry>> facetFieldResults = new HashMap<org.springframework.data.solr.core.query.Field, Page<FacetFieldEntry>>();
            facetPage.getFacetFields().forEach(field -> facetFieldResults.put(field, facetPage.getFacetResultPage(field)));
            results.addAllFacetFieldResultPages(facetFieldResults);
        }

        // only process highlight if selected in GraphQL query
        if (highlightsSelected) {
            List<HighlightEntry<ND>> highlighted = facetPage.getHighlighted().stream().map(entry -> {
                HighlightEntry<ND> ndEntry = new HighlightEntry<>(documents.get(entry.getEntity().getId()));
                entry.getHighlights().forEach(highlights -> {
                    ndEntry.addSnipplets(highlights.getField(), highlights.getSnipplets());
                });
                return ndEntry;
            }).collect(Collectors.toList());
            results.setHighlighted(highlighted);
        }
        return DiscoveryFacetAndHighlightPage.from((FacetAndHighlightPage<ND>) results, facets, highlight);
    }

    @Override
    public List<ND> findBySyncIdsIn(List<String> syncIds) {
        return repo.findBySyncIdsIn(syncIds).stream().map(document -> toNested(document, new ArrayList<Field>())).collect(Collectors.toList());
    }

    /* For Debugging @@@ erik */
    public void dumpContentMap(Map<String, List<String>> map) {
        System.out.println("\n\n** Content Map Dump **\n");
        Set<String> contentKeys = map.keySet();
        Iterator<String> keyIter = contentKeys.iterator();
        while (keyIter.hasNext()) {
            String key = keyIter.next();
            System.out.println("fieldName contained in content: " + key);
            List<String> curValues = map.get(key);
            if (curValues != null) {
                Iterator iter = curValues.iterator();
                String s = "";
                while (iter.hasNext()) {
                    s += (iter.next() + "\n");
                }
                System.out.println("values for fieldName: " + key + " \nlist: " + s);
            } else {
                System.out.println("No Values for fieldName: " + key);
            }
        }
    }
    public Map<String, List<String>> adjustModTime(Map<String, List<String>> map) { // @@@ erik
        // NOTE! - returns a modified version of the input rather than a deep copy.
        List<String> curModTime = map.get("modTime"); // for now, just overwrite rather than trying to re-format
        ArrayList<String> newModTime = new ArrayList<String>();
        String modTime = ZonedDateTime.now( ZoneOffset.UTC ).format( DateTimeFormatter.ISO_INSTANT );
        newModTime.add(modTime);
        map.put("modTime", newModTime);
        return map;
    }
    public ND updateFieldValue(String id, List<Field> fields, String fieldName, String value) { // @@@ erik
       Optional<ND> nestedDocument = Optional.empty();
       Optional<Individual> document = repo.findById(id);
       if (document.isPresent()) {
           Individual originalDetail = document.get();
           Map<String, List<String>> content = originalDetail.getContent();
           this.dumpContentMap(content);

           ArrayList<String> newValues = new ArrayList<String>();
           newValues.add(value);
           content.put(fieldName, (List<String>)newValues);

           content = this.adjustModTime(content);

           originalDetail.setContent(content);
           this.dumpContentMap(content);

           this.repo.save(originalDetail);
           nestedDocument = Optional.of(toNested(originalDetail, fields));
           return nestedDocument.get();
       }
       throw new DocumentNotFoundException(String.format("Could not find %s with id %s", type(), id));
    }

    private Optional<ND> findById(String id, List<Field> fields) {
        Optional<ND> nestedDocument = Optional.empty();
        Optional<Individual> document = repo.findById(id);
        if (document.isPresent()) {
            nestedDocument = Optional.of(toNested(document.get(), fields));
        }
        return nestedDocument;
    }

    private ND toNested(Individual document, List<Field> fields) {
        ObjectNode node = mapper.valueToTree(document);
        Optional<Composite> composite = composites.stream().filter(c -> c.getType().equals(type().getSimpleName())).findAny();
        if (composite.isPresent()) {
            composite.get().getReferences().parallelStream().forEach(reference -> {
                if (node.has(reference.getName()) && dereference(reference, fields)) {
                    JsonNode referenceNode = node.get(reference.getName());
                    List<String> ids = new ArrayList<String>();
                    if (referenceNode.isArray()) {
                        Iterator<JsonNode> resourceNodes = ((ArrayNode) referenceNode).elements();
                        while (resourceNodes.hasNext()) {
                            ids.add(resourceNodes.next().get(ID).asText());
                        }
                    } else {
                        ids.add(referenceNode.get(ID).asText());
                    }

                    Optional<NestedDocumentService<?>> nestedDocumentService = nestedDocumentServices.stream().filter(service -> {
                        return service.type().getSimpleName().equals(reference.getType());
                    }).findAny();

                    if (nestedDocumentService.isPresent()) {
                        List<AbstractNestedDocument> references = new ArrayList<AbstractNestedDocument>();
                        while (ids.size() >= MAX_BATCH_SIZE) {
                            references.addAll(nestedDocumentService.get().findByIdIn(ids.subList(0, MAX_BATCH_SIZE), fields));
                            ids = ids.subList(MAX_BATCH_SIZE, ids.size());
                        }
                        references.addAll(nestedDocumentService.get().findByIdIn(ids, fields));
                        node.set(reference.getName(), mapper.valueToTree(references));
                    }
                } else {
                    node.remove(reference.getName());
                }
            });
        }
        return mapper.convertValue(node, type());
    }

    private boolean selected(List<Field> fields, String fieldName) {
        return fields.stream().filter(f -> f.getSelectionSet().getSelections().stream().filter(s -> ((Field) s).getName().equals(fieldName)).findAny().isPresent()).findAny().isPresent();
    }

    @SuppressWarnings("rawtypes")
    private boolean dereference(CompositeReference reference, List<Field> fields) {
        for (Field field : fields) {
            List<Selection> selections = field.getSelectionSet().getSelections();
            if (checkSelections(reference, selections)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean checkSelections(CompositeReference reference, List<Selection> selections) {
        for (Selection selection : selections) {
            if (checkSelection(reference, (Field) selection)) {
                return true;
            }
            if (!selection.getChildren().isEmpty()) {
                List<SelectionSet> sets = selection.getChildren();
                for (SelectionSet set : sets) {
                    if (checkSelections(reference, set.getSelections())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkSelection(CompositeReference reference, Field field) {
        return field.getName().equals(reference.getName());
    }

    protected abstract Class<?> getOriginDocumentType();

    protected List<FilterArg> augmentFilters(List<FilterArg> filters) {
        filters.add(getCollectionFilter());
        return filters;
    }

    protected FilterArg getCollectionFilter() {
        return FilterArg.of(CLASS, Optional.of(type().getSimpleName()), Optional.of(OpKey.EQUALS.getKey()), Optional.empty());
    }

}
