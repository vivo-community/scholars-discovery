package edu.tamu.scholars.middleware.graphql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.IndexArg;
import edu.tamu.scholars.middleware.discovery.model.repo.OrganizationRepo;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryPage;
import edu.tamu.scholars.middleware.graphql.model.Organization;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class OrganizationService extends AbstractNestedDocumentService<Organization, edu.tamu.scholars.middleware.discovery.model.Organization, OrganizationRepo> {

    @Override
    @GraphQLQuery(name = "organizationExistsById")
    public boolean existsById(@GraphQLArgument(name = "id") String id) {
        return super.existsById(id);
    }

    @Override
    @GraphQLQuery(name = "organizationById")
    public Organization getById(@GraphQLArgument(name = "id") String id) {
        return super.getById(id);
    }

    @Override
    @GraphQLQuery(name = "organizationsCount")
    public long count() {
        return super.count();
    }

    @Override
    @GraphQLQuery(name = "organizationsCount")
    // @formatter:off
    public long count(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters
    ) {
    // @formatter:on
        return super.count(query, filters);
    }

    @Override
    @GraphQLQuery(name = "organizationsSorted")
    public Iterable<Organization> findAll(@GraphQLArgument(name = "sort") Sort sort) {
        return super.findAll(sort);
    }

    @Override
    @GraphQLQuery(name = "organizationsPaged")
    public DiscoveryPage<Organization> findAllPaged(@GraphQLArgument(name = "paging") Pageable page) {
        return super.findAllPaged(page);
    }

    @Override
    @GraphQLQuery(name = "organizationsSearch")
    // @formatter:off
    public DiscoveryFacetPage<Organization> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.search(query, page);
    }

    @Override
    @GraphQLQuery(name = "organizationsSearch")
    // @formatter:off
    public DiscoveryFacetPage<Organization> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "boosts") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.search(query, boosts, page);
    }

    @Override
    @GraphQLQuery(name = "organizationsFilterSearch")
    // @formatter:off
    public DiscoveryFacetPage<Organization> filterSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.filterSearch(query, filters, page);
    }

    @Override
    @GraphQLQuery(name = "organizationsFilterSearch")
    // @formatter:off
    public DiscoveryFacetPage<Organization> filterSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "boosts") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.filterSearch(query, filters, boosts, page);
    }

    @Override
    @GraphQLQuery(name = "organizationsFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Organization> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, page);
    }

    @Override
    @GraphQLQuery(name = "organizationsFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Organization> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, page);
    }

    @Override
    @GraphQLQuery(name = "organizationsFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Organization> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "boosts") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, boosts, page);
    }

    @Override
    @GraphQLQuery(name = "organizationsFacetedSearchIndex")
    // @formatter:off
    public DiscoveryFacetPage<Organization> facetedSearch(
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
    @GraphQLQuery(name = "organizationsFacetedSearchIndex")
    // @formatter:off
    public DiscoveryFacetPage<Organization> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "index") Optional<IndexArg> index,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "boosts") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, index, facets, filters, boosts, page);
    }

    @Override
    @GraphQLQuery(name = "organizationsByType")
    public List<Organization> findByType(@GraphQLArgument(name = "type") String type) {
        return super.findByType(type);
    }

    @Override
    @GraphQLQuery(name = "organizationsByIds")
    public List<Organization> findByIdIn(@GraphQLArgument(name = "ids") List<String> ids) {
        return super.findByIdIn(ids);
    }

    @Override
    @GraphQLQuery(name = "organizationsMostRecentlyUpdate")
    public List<Organization> findMostRecentlyUpdate(@GraphQLArgument(name = "limit") Integer limit) {
        return super.findMostRecentlyUpdate(limit);
    }

    @Override
    public Class<Organization> type() {
        return Organization.class;
    }

    @Override
    protected Class<?> getOriginDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.Organization.class;
    }

}
