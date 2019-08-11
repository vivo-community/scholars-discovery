package edu.tamu.scholars.middleware.graphql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.IndexArg;
import edu.tamu.scholars.middleware.discovery.model.repo.ConceptRepo;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryPage;
import edu.tamu.scholars.middleware.graphql.model.Concept;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class ConceptService extends AbstractNestedDocumentService<Concept, edu.tamu.scholars.middleware.discovery.model.Concept, ConceptRepo> {

    @Override
    @GraphQLQuery(name = "conceptExistsById")
    public boolean existsById(@GraphQLArgument(name = "id") String id) {
        return super.existsById(id);
    }

    @Override
    @GraphQLQuery(name = "conceptById")
    public Concept getById(@GraphQLArgument(name = "id") String id) {
        return super.getById(id);
    }

    @Override
    @GraphQLQuery(name = "conceptsCount")
    public long count() {
        return super.count();
    }

    @Override
    @GraphQLQuery(name = "conceptsCount")
    // @formatter:off
    public long count(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters
    ) {
    // @formatter:on
        return super.count(query, filters);
    }

    @Override
    @GraphQLQuery(name = "conceptsSorted")
    public Iterable<Concept> findAll(@GraphQLArgument(name = "sort") Sort sort) {
        return super.findAll(sort);
    }

    @Override
    @GraphQLQuery(name = "conceptsPaged")
    public DiscoveryPage<Concept> findAllPaged(@GraphQLArgument(name = "paging") Pageable page) {
        return super.findAllPaged(page);
    }

    @Override
    @GraphQLQuery(name = "conceptsSearch")
    // @formatter:off
    public DiscoveryFacetPage<Concept> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.search(query, page);
    }

    @Override
    @GraphQLQuery(name = "conceptsFilterSearch")
    // @formatter:off
    public DiscoveryFacetPage<Concept> filterSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.filterSearch(query, filters, page);
    }

    @Override
    @GraphQLQuery(name = "conceptsFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Concept> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, page);
    }

    @Override
    @GraphQLQuery(name = "conceptsFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Concept> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, page);
    }

    @Override
    @GraphQLQuery(name = "conceptsFacetedSearchIndex")
    // @formatter:off
    public DiscoveryFacetPage<Concept> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "index") Optional<IndexArg> index,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, index, facets, filters, page);
    }

    @Override
    @GraphQLQuery(name = "conceptsByType")
    public List<Concept> findByType(@GraphQLArgument(name = "type") String type) {
        return super.findByType(type);
    }

    @Override
    @GraphQLQuery(name = "conceptsByIds")
    public List<Concept> findByIdIn(@GraphQLArgument(name = "ids") List<String> ids) {
        return super.findByIdIn(ids);
    }

    @Override
    @GraphQLQuery(name = "conceptsMostRecentlyUpdate")
    public List<Concept> findMostRecentlyUpdate(@GraphQLArgument(name = "limit") Integer limit) {
        return super.findMostRecentlyUpdate(limit);
    }

    @Override
    public Class<Concept> type() {
        return Concept.class;
    }

    @Override
    protected Class<?> getOriginDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.Concept.class;
    }

}
