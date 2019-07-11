package edu.tamu.scholars.middleware.graphql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.IndexArg;
import edu.tamu.scholars.middleware.discovery.model.repo.RelationshipRepo;
import edu.tamu.scholars.middleware.graphql.model.Relationship;
import edu.tamu.scholars.middleware.graphql.type.GraphQLFacetPage;
import edu.tamu.scholars.middleware.graphql.type.GraphQLPage;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class RelationshipService extends AbstractNestedDocumentService<Relationship, edu.tamu.scholars.middleware.discovery.model.Relationship, RelationshipRepo> {

    @Override
    @GraphQLQuery(name = "relationshipExistsById")
    public boolean existsById(@GraphQLArgument(name = "id") String id) {
        return super.existsById(id);
    }

    @Override
    @GraphQLQuery(name = "relationshipById")
    public Relationship getById(@GraphQLArgument(name = "id") String id) {
        return super.getById(id);
    }

    @Override
    @GraphQLQuery(name = "relationshipsCount")
    public long count() {
        return super.count();
    }

    @Override
    @GraphQLQuery(name = "relationshipsCount")
    // @formatter:off
    public long count(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters
    ) {
    // @formatter:on
        return super.count(query, filters);
    }

    @Override
    @GraphQLQuery(name = "relationshipsSorted")
    public Iterable<Relationship> findAll(@GraphQLArgument(name = "sort") Sort sort) {
        return super.findAll(sort);
    }

    @Override
    @GraphQLQuery(name = "relationshipsPaged")
    public GraphQLPage<Relationship> findAllPaged(@GraphQLArgument(name = "paging") Pageable page) {
        return super.findAllPaged(page);
    }

    @Override
    @GraphQLQuery(name = "relationshipsSearch")
    // @formatter:off
    public GraphQLFacetPage<Relationship> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.search(query, page);
    }

    @Override
    @GraphQLQuery(name = "relationshipsFilterSearch")
    // @formatter:off
    public GraphQLFacetPage<Relationship> filterSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.filterSearch(query, filters, page);
    }

    @Override
    @GraphQLQuery(name = "relationshipsFacetedSearch")
    // @formatter:off
    public GraphQLFacetPage<Relationship> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, page);
    }

    @Override
    @GraphQLQuery(name = "relationshipsFacetedSearch")
    // @formatter:off
    public GraphQLFacetPage<Relationship> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, page);
    }

    @Override
    @GraphQLQuery(name = "relationshipsFacetedSearchIndex")
    // @formatter:off
    public GraphQLFacetPage<Relationship> facetedSearch(
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
    @GraphQLQuery(name = "relationshipsByType")
    public List<Relationship> findByType(@GraphQLArgument(name = "type") String type) {
        return super.findByType(type);
    }

    @Override
    @GraphQLQuery(name = "relationshipsByIds")
    public List<Relationship> findByIdIn(@GraphQLArgument(name = "ids") List<String> ids) {
        return super.findByIdIn(ids);
    }

    @Override
    @GraphQLQuery(name = "relationshipsMostRecentlyUpdate")
    public List<Relationship> findMostRecentlyUpdate(@GraphQLArgument(name = "limit") Integer limit) {
        return super.findMostRecentlyUpdate(limit);
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Relationship.class;
    }

    @Override
    protected Class<?> getOriginDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.Relationship.class;
    }

}
