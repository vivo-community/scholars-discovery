package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryPage;
import edu.tamu.scholars.middleware.graphql.model.Concept;
import graphql.language.Field;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import edu.tamu.scholars.middleware.graphql.service.DefaultablePageRequest;

@Service
public class ConceptService extends AbstractNestedDocumentService<Concept> {

    @Override
    //@GraphQLQuery(name = "conceptExistsById")
    public boolean existsById(@GraphQLArgument(name = "id") String id) {
        return super.existsById(id);
    }

    @Override
    @GraphQLQuery(name = "concept")
    // @formatter:off
    public Concept getById(
        @GraphQLArgument(name = "id") String id,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.getById(id, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsByType")
    // @formatter:off
    public List<Concept> findByType(
        @GraphQLArgument(name = "type") String type,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findByType(type, new ArrayList<FilterArg>(), fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsByIds")
    // @formatter:off
    public List<Concept> findByIdIn(
        @GraphQLArgument(name = "ids") List<String> ids,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findByIdIn(ids, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsMostRecentlyUpdate")
    // @formatter:off
    public List<Concept> findMostRecentlyUpdate(
        @GraphQLArgument(name = "limit") Integer limit,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findMostRecentlyUpdate(limit, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsMostRecentlyUpdate")
    // @formatter:off
    public List<Concept> findMostRecentlyUpdate(
        @GraphQLArgument(name = "limit") Integer limit,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findMostRecentlyUpdate(limit, filters, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsCount")
    public long count() {
        return super.count();
    }

    @Override
    //@GraphQLQuery(name = "conceptsCount")
    // @formatter:off
    public long count(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters
    ) {
    // @formatter:on
        return super.count(query, filters);
    }

    @Override
    //@GraphQLQuery(name = "conceptsSorted")
    // @formatter:off
    public Iterable<Concept> findAll(
        @GraphQLArgument(name = "sort") Sort sort,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findAll(new ArrayList<FilterArg>(), sort, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsPaged")
    // @formatter:off
    public DiscoveryPage<Concept> findAll(
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findAll(new ArrayList<FilterArg>(), page, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsSearch")
    // @formatter:off
    public DiscoveryFacetPage<Concept> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.search(query, page, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsSearch")
    // @formatter:off
    public DiscoveryFacetPage<Concept> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "boosts") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.search(query, boosts, page, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsFilterSearch")
    // @formatter:off
    public DiscoveryFacetPage<Concept> filterSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.filterSearch(query, filters, page, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsFilterSearch")
    // @formatter:off
    public DiscoveryFacetPage<Concept> filterSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "boosts") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.filterSearch(query, filters, boosts, page, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Concept> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, page, fields);
    }

    @Override
    //@GraphQLQuery(name = "conceptsFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Concept> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, page, fields);
    }

    //@Override
    @GraphQLQuery(name = "concepts")
    // @formatter:off
    public DiscoveryFacetPage<Concept> facetedSearch(
        @GraphQLArgument(name = "query", defaultValue="*") String query,
        @GraphQLArgument(name = "facets", defaultValue="[]") List<FacetArg> facets,
        @GraphQLArgument(name = "filters", defaultValue="[]") List<FilterArg> filters,
        @GraphQLArgument(name = "boosts", defaultValue="[]") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging", defaultValue="{}") DefaultablePageRequest page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, boosts, page, fields);
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
