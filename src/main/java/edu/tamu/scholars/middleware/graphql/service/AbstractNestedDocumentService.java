package edu.tamu.scholars.middleware.graphql.service;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.util.ReflectionUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryPage;
import edu.tamu.scholars.middleware.graphql.config.model.Composite;
import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import graphql.language.Field;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@GraphQLApi
public abstract class AbstractNestedDocumentService<ND extends AbstractNestedDocument, D extends AbstractSolrDocument, R extends SolrDocumentRepo<D>> implements SolrDocumentRepo<ND> {

    @Autowired
    private R repo;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private List<SolrDocumentRepo<?>> solrDocumentRepos;

    private final List<Composite> composites = new ArrayList<Composite>();

    @PostConstruct
    public void init() throws JsonParseException, JsonMappingException, IOException {
        Resource compositesResource = new ClassPathResource("defaults/composites.yml");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        // @formatter:off
        composites.addAll(mapper.readValue(compositesResource.getInputStream(), new TypeReference<List<Composite>>() {}));
        // @formatter:on
    }

    @Override
    public <NS extends ND> NS save(NS nestedDocument, Duration commitWithin) {
        throw new UnsupportedOperationException(String.format("%s is read only", type()));
    }

    @Override
    public <NS extends ND> Iterable<NS> saveAll(Iterable<NS> nestedDocuments, Duration commitWithin) {
        throw new UnsupportedOperationException(String.format("%s is read only", type()));
    }

    @Override
    public long count() {
        return repo.count();
    }

    public Iterable<ND> findAll(Sort sort, List<Field> fields) {
        return StreamSupport.stream(repo.findAll(sort).spliterator(), false).map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public Iterable<ND> findAll(Sort sort) {
        throw new UnsupportedOperationException("Find all without GraphQL query fields is not supported.");
    }

    public DiscoveryPage<ND> findAll(Pageable page, List<Field> fields) {
        return DiscoveryPage.from(repo.findAll(page).map(document -> toNested(document, fields)));
    }

    @Override
    public Page<ND> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Find all  without GraphQL query fields is not supported.");
    }

    @Override
    public <NS extends ND> Iterable<NS> saveAll(Iterable<NS> nestedDocuments) {
        throw new UnsupportedOperationException(String.format("%s is read only", type()));
    }

    public Optional<ND> findById(String id, List<Field> fields) {
        Optional<ND> nestedDocument = Optional.empty();
        Optional<D> document = repo.findById(id);
        if (document.isPresent()) {
            nestedDocument = Optional.of(toNested(document.get(), fields));
        }
        return nestedDocument;
    }

    @Override
    public Optional<ND> findById(String id) {
        throw new UnsupportedOperationException("Find by id without GraphQL query fields is not supported.");
    }

    @Override
    public boolean existsById(String id) {
        return repo.existsById(id);
    }

    public Iterable<ND> findAll(List<Field> fields) {
        return StreamSupport.stream(repo.findAll().spliterator(), false).map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public Iterable<ND> findAll() {
        throw new UnsupportedOperationException("Find all without GraphQL query fields is not supported.");
    }

    public Iterable<ND> findAllById(Iterable<String> ids, List<Field> fields) {
        return StreamSupport.stream(repo.findAllById(ids).spliterator(), false).map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public Iterable<ND> findAllById(Iterable<String> ids) {
        throw new UnsupportedOperationException("Find all by id without GraphQL query fields is not supported.");
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException(String.format("%s is read only", type()));
    }

    @Override
    public void deleteAll(Iterable<? extends ND> nestedDocuments) {
        throw new UnsupportedOperationException(String.format("%s is read only", type()));
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException(String.format("%s is read only", type()));
    }

    @SuppressWarnings("unchecked")
    public FacetPage<ND> search(String query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, Pageable page, List<Field> fields) {
        FacetPage<D> facetPage = repo.search(query, facets, filters, boosts, page);
        List<ND> content = facetPage.getContent().stream().map(document -> toNested(document, fields)).collect(Collectors.toList());
        java.lang.reflect.Field field = FieldUtils.getField(SolrResultPage.class, "content", true);
        ReflectionUtils.setField(field, facetPage, content);
        return (FacetPage<ND>) facetPage;
    }

    @Override
    public FacetPage<ND> search(String query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, Pageable page) {
        throw new UnsupportedOperationException("Search without GraphQL query fields is not supported.");
    }

    public DiscoveryFacetPage<ND> search(String query, Pageable page, List<Field> fields) {
        return facetedSearch(query, new ArrayList<FacetArg>(), new ArrayList<FilterArg>(), new ArrayList<BoostArg>(), page, fields);
    }

    public DiscoveryFacetPage<ND> search(String query, List<BoostArg> boosts, Pageable page, List<Field> fields) {
        return facetedSearch(query, new ArrayList<FacetArg>(), new ArrayList<FilterArg>(), boosts, page, fields);
    }

    public DiscoveryFacetPage<ND> filterSearch(String query, List<FilterArg> filters, Pageable page, List<Field> fields) {
        return facetedSearch(query, new ArrayList<FacetArg>(), filters, new ArrayList<BoostArg>(), page, fields);
    }

    public DiscoveryFacetPage<ND> filterSearch(String query, List<FilterArg> filters, List<BoostArg> boosts, Pageable page, List<Field> fields) {
        return facetedSearch(query, new ArrayList<FacetArg>(), filters, boosts, page, fields);
    }

    public DiscoveryFacetPage<ND> facetedSearch(String query, List<FacetArg> facets, Pageable page, List<Field> fields) {
        return facetedSearch(query, facets, new ArrayList<FilterArg>(), new ArrayList<BoostArg>(), page, fields);
    }

    public DiscoveryFacetPage<ND> facetedSearch(String query, List<FacetArg> facets, List<FilterArg> filters, Pageable page, List<Field> fields) {
        return facetedSearch(query, facets, filters, new ArrayList<BoostArg>(), page, fields);
    }

    public DiscoveryFacetPage<ND> facetedSearch(String query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, Pageable page, List<Field> fields) {
        return facetedSearch(query, facets, filters, boosts, page, fields);
    }

    public List<Composite> getComposites() {
        return composites;
    }

    @Override
    public long count(String query, List<FilterArg> filters) {
        return repo.count(query, filters);
    }

    @Override
    public <NS extends ND> NS save(NS nestedDocument) {
        throw new UnsupportedOperationException(String.format("%s is read only", type()));
    }

    @Override
    public void delete(ND nestedDocument) {
        throw new UnsupportedOperationException(String.format("%s is read only", type()));
    }

    public List<ND> findByType(String type, List<Field> fields) {
        return repo.findByType(type).stream().map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public List<ND> findByType(String type) {
        throw new UnsupportedOperationException("Find by type without GraphQL query fields is not supported.");
    }

    public List<ND> findByIdIn(List<String> ids, List<Field> fields) {
        return repo.findByIdIn(ids).stream().map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public List<ND> findByIdIn(List<String> ids) {
        throw new UnsupportedOperationException("Find by id in without GraphQL query fields is not supported.");
    }

    public List<ND> findBySyncIds(String syncId, List<Field> fields) {
        return repo.findBySyncIds(syncId).stream().map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public List<ND> findBySyncIds(String syncId) {
        throw new UnsupportedOperationException("Find by sync ids without GraphQL query fields is not supported.");
    }

    public List<ND> findBySyncIdsIn(List<String> syncIds, List<Field> fields) {
        return repo.findBySyncIdsIn(syncIds).stream().map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public List<ND> findBySyncIdsIn(List<String> syncIds) {
        throw new UnsupportedOperationException("Find by sync ids in without GraphQL query fields is not supported.");
    }

    @Override
    public List<ND> findMostRecentlyUpdate(Integer limit) {
        throw new UnsupportedOperationException("Find most recently update without GraphQL query fields is not supported.");
    }

    @Override
    public List<ND> findMostRecentlyUpdate(Integer limit, List<FilterArg> filter) {
        throw new UnsupportedOperationException("Find most recently update without GraphQL query fields is not supported.");
    }

    public List<ND> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters, List<Field> fields) {
        return repo.findMostRecentlyUpdate(limit, filters).stream().map(document -> toNested(document, fields)).collect(Collectors.toList());
    }

    @Override
    public Cursor<ND> stream(String query, List<FilterArg> filters, List<BoostArg> boosts, Sort sort) {
        throw new UnsupportedOperationException("Streaming is currently unsupported");
    }

    protected abstract Class<?> getOriginDocumentType();

    private ND toNested(D document, List<Field> fields) {
        // TODO: only fetch reference if a requested field
        // TODO: recursively dereference
        ObjectNode node = mapper.valueToTree(document);
        Optional<Composite> composite = composites.stream().filter(c -> c.getType().equals(type().getSimpleName())).findAny();
        if (composite.isPresent()) {
            composite.get().getReferences().parallelStream().forEach(reference -> {
                if (node.has(reference.getName())) {
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
                    Optional<SolrDocumentRepo<?>> service = solrDocumentRepos.stream().filter(repo -> {
                        return repo instanceof AbstractNestedDocumentService && repo.type().getSimpleName().equals(reference.getType());
                    }).findAny();
                    if (service.isPresent()) {
                        node.set(reference.getName(), mapper.valueToTree(service.get().findByIdIn(ids)));
                    }
                }
            });
        }
        return mapper.convertValue(node, type());
    }

}
