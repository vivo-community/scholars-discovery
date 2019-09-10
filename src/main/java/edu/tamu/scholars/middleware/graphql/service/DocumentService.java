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
import edu.tamu.scholars.middleware.graphql.model.Document;
import graphql.language.Field;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class DocumentService extends AbstractNestedDocumentService<Document> {

    @Override
    @GraphQLQuery(name = "documentExistsById")
    public boolean existsById(@GraphQLArgument(name = "id") String id) {
        return super.existsById(id);
    }

    @Override
    @GraphQLQuery(name = "documentById")
    // @formatter:off
    public Document getById(
        @GraphQLArgument(name = "id") String id,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.getById(id, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsByType")
    // @formatter:off
    public List<Document> findByType(
        @GraphQLArgument(name = "type") String type,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findByType(type, new ArrayList<FilterArg>(), fields);
    }

    @Override
    @GraphQLQuery(name = "documentsByIds")
    // @formatter:off
    public List<Document> findByIdIn(
        @GraphQLArgument(name = "ids") List<String> ids,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findByIdIn(ids, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsMostRecentlyUpdate")
    // @formatter:off
    public List<Document> findMostRecentlyUpdate(
        @GraphQLArgument(name = "limit") Integer limit,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findMostRecentlyUpdate(limit, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsMostRecentlyUpdate")
    // @formatter:off
    public List<Document> findMostRecentlyUpdate(
        @GraphQLArgument(name = "limit") Integer limit,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findMostRecentlyUpdate(limit, filters, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsCount")
    public long count() {
        return super.count();
    }

    @Override
    @GraphQLQuery(name = "documentsCount")
    // @formatter:off
    public long count(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters
    ) {
    // @formatter:on
        return super.count(query, filters);
    }

    @Override
    @GraphQLQuery(name = "documentsSorted")
    // @formatter:off
    public Iterable<Document> findAll(
        @GraphQLArgument(name = "sort") Sort sort,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findAll(new ArrayList<FilterArg>(), sort, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsPaged")
    // @formatter:off
    public DiscoveryPage<Document> findAll(
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findAll(new ArrayList<FilterArg>(), page, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsSearch")
    // @formatter:off
    public DiscoveryFacetPage<Document> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.search(query, page, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsSearch")
    // @formatter:off
    public DiscoveryFacetPage<Document> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "boosts") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.search(query, boosts, page, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsFilterSearch")
    // @formatter:off
    public DiscoveryFacetPage<Document> filterSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.filterSearch(query, filters, page, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsFilterSearch")
    // @formatter:off
    public DiscoveryFacetPage<Document> filterSearch(
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
    @GraphQLQuery(name = "documentsFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Document> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, page, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Document> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, page, fields);
    }

    @Override
    @GraphQLQuery(name = "documentsFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Document> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "boosts") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, boosts, page, fields);
    }

    @Override
    public Class<Document> type() {
        return Document.class;
    }

    @Override
    protected Class<?> getOriginDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.Document.class;
    }

}
