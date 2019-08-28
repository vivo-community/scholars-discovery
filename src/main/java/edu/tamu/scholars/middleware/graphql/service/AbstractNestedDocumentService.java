package edu.tamu.scholars.middleware.graphql.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryPage;
import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@GraphQLApi
public abstract class AbstractNestedDocumentService<ND extends AbstractNestedDocument, D extends AbstractSolrDocument, R extends SolrDocumentRepo<D>> implements SolrDocumentRepo<ND> {

    @Autowired
    private R repo;

    @Autowired
    private ObjectMapper mapper;

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

    @Override
    public Iterable<ND> findAll(Sort sort) {
        return StreamSupport.stream(repo.findAll(sort).spliterator(), false).map(this::toNested).collect(Collectors.toList());
    }

    public DiscoveryPage<ND> findAllPaged(Pageable page) {
        return DiscoveryPage.from(findAll(page));
    }

    @Override
    public Page<ND> findAll(Pageable page) {
        return repo.findAll(page).map(this::toNested);
    }

    @Override
    public <NS extends ND> Iterable<NS> saveAll(Iterable<NS> nestedDocuments) {
        throw new UnsupportedOperationException(String.format("%s is read only", type()));
    }

    @Override
    public Optional<ND> findById(String id) {
        Optional<ND> nestedDocument = Optional.empty();
        Optional<D> document = repo.findById(id);
        if (document.isPresent()) {
            nestedDocument = Optional.of(toNested(document.get()));
        }
        return nestedDocument;
    }

    public ND getById(String id) {
        return findById(id).get();
    }

    @Override
    public boolean existsById(String id) {
        return repo.existsById(id);
    }

    @Override
    public Iterable<ND> findAll() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public Iterable<ND> findAllById(Iterable<String> ids) {
        return StreamSupport.stream(repo.findAllById(ids).spliterator(), false).map(this::toNested).collect(Collectors.toList());
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

    @Override
    @SuppressWarnings("unchecked")
    public FacetPage<ND> search(String query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, Pageable page) {
        FacetPage<D> facetPage = repo.search(query, facets, filters, boosts, page);
        List<ND> content = facetPage.getContent().stream().map(this::toNested).collect(Collectors.toList());
        Field field = FieldUtils.getField(SolrResultPage.class, "content", true);
        ReflectionUtils.setField(field, facetPage, content);
        return (FacetPage<ND>) facetPage;
    }

    public DiscoveryFacetPage<ND> search(String query, Pageable page) {
        return facetedSearch(query, new ArrayList<FacetArg>(), new ArrayList<FilterArg>(), new ArrayList<BoostArg>(), page);
    }

    public DiscoveryFacetPage<ND> search(String query, List<BoostArg> boosts, Pageable page) {
        return facetedSearch(query, new ArrayList<FacetArg>(), new ArrayList<FilterArg>(), boosts, page);
    }

    public DiscoveryFacetPage<ND> filterSearch(String query, List<FilterArg> filters, Pageable page) {
        return facetedSearch(query, new ArrayList<FacetArg>(), filters, new ArrayList<BoostArg>(), page);
    }

    public DiscoveryFacetPage<ND> filterSearch(String query, List<FilterArg> filters, List<BoostArg> boosts, Pageable page) {
        return facetedSearch(query, new ArrayList<FacetArg>(), filters, boosts, page);
    }

    public DiscoveryFacetPage<ND> facetedSearch(String query, List<FacetArg> facets, Pageable page) {
        return facetedSearch(query, facets, new ArrayList<FilterArg>(), new ArrayList<BoostArg>(), page);
    }

    public DiscoveryFacetPage<ND> facetedSearch(String query, List<FacetArg> facets, List<FilterArg> filters, Pageable page) {
        return facetedSearch(query, facets, filters, new ArrayList<BoostArg>(), page);
    }

    public DiscoveryFacetPage<ND> facetedSearch(String query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, Pageable page) {
        FacetPage<ND> facetPage = search(query, facets, filters, boosts, page);
        return DiscoveryFacetPage.from(facetPage, facets, getOriginDocumentType());
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

    @Override
    public List<ND> findByType(String type) {
        return repo.findByType(type).stream().map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public List<ND> findByIdIn(List<String> ids) {
        return repo.findByIdIn(ids).stream().map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public List<ND> findBySyncIds(String syncId) {
        return repo.findBySyncIds(syncId).stream().map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public List<ND> findBySyncIdsIn(List<String> syncIds) {
        return repo.findBySyncIdsIn(syncIds).stream().map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public List<ND> findMostRecentlyUpdate(Integer limit) {
        return repo.findMostRecentlyUpdate(limit).stream().map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public List<ND> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters) {
        return repo.findMostRecentlyUpdate(limit, filters).stream().map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public Cursor<ND> stream(String query, List<FilterArg> filters, List<BoostArg> boosts, Sort sort) {
        throw new UnsupportedOperationException("Streaming is currently unsupported");
    }

    protected abstract Class<?> getOriginDocumentType();

    private ND toNested(D document) {
        try {
            String json = mapper.writeValueAsString(document);
            return mapper.readValue(json, type());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }

}
