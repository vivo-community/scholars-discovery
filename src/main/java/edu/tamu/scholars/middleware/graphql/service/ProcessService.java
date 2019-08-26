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
import edu.tamu.scholars.middleware.discovery.model.repo.ProcessRepo;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryPage;
import edu.tamu.scholars.middleware.graphql.exception.DocumentNotFoundException;
import edu.tamu.scholars.middleware.graphql.model.Process;
import graphql.language.Field;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class ProcessService extends AbstractNestedDocumentService<Process, edu.tamu.scholars.middleware.discovery.model.Process, ProcessRepo> {

    @Override
    @GraphQLQuery(name = "processExistsById")
    public boolean existsById(@GraphQLArgument(name = "id") String id) {
        return super.existsById(id);
    }

    @GraphQLQuery(name = "processById")
    // @formatter:off
    public Process getById(
        @GraphQLArgument(name = "id") String id,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        Optional<Process> document = super.findById(id, fields);
        if (document.isPresent()) {
            return document.get();
        }
        throw new DocumentNotFoundException(String.format("Could not find %s with id %s", type(), id));
    }

    @Override
    @GraphQLQuery(name = "processesCount")
    public long count() {
        return super.count();
    }

    @Override
    @GraphQLQuery(name = "processesCount")
    // @formatter:off
    public long count(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters
    ) {
    // @formatter:on
        return super.count(query, filters);
    }

    @Override
    @GraphQLQuery(name = "processesSorted")
    // @formatter:off
    public Iterable<Process> findAll(
        @GraphQLArgument(name = "sort") Sort sort,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findAll(sort, fields);
    }

    @Override
    @GraphQLQuery(name = "processesPaged")
    // @formatter:off
    public DiscoveryPage<Process> findAll(
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findAll(page, fields);
    }

    @Override
    @GraphQLQuery(name = "processesSearch")
    // @formatter:off
    public DiscoveryFacetPage<Process> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.search(query, page, fields);
    }

    @Override
    @GraphQLQuery(name = "processesSearch")
    // @formatter:off
    public DiscoveryFacetPage<Process> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "boosts") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.search(query, boosts, page, fields);
    }

    @Override
    @GraphQLQuery(name = "processesFilterSearch")
    // @formatter:off
    public DiscoveryFacetPage<Process> filterSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.filterSearch(query, filters, page, fields);
    }

    @Override
    @GraphQLQuery(name = "processesFilterSearch")
    // @formatter:off
    public DiscoveryFacetPage<Process> filterSearch(
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
    @GraphQLQuery(name = "processesFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Process> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, page, fields);
    }

    @Override
    @GraphQLQuery(name = "processesFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Process> facetedSearch(
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
    @GraphQLQuery(name = "processesFacetedSearch")
    // @formatter:off
    public DiscoveryFacetPage<Process> facetedSearch(
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
    @GraphQLQuery(name = "processesFacetedSearchIndex")
    // @formatter:off
    public DiscoveryFacetPage<Process> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "index") Optional<IndexArg> index,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.facetedSearch(query, index, facets, filters, page, fields);
    }

    @Override
    @GraphQLQuery(name = "processesFacetedSearchIndex")
    // @formatter:off
    public DiscoveryFacetPage<Process> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "index") Optional<IndexArg> index,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "boosts") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.facetedSearch(query, index, facets, filters, boosts, page, fields);
    }

    @Override
    @GraphQLQuery(name = "processesByType")
    // @formatter:off
    public List<Process> findByType(
        @GraphQLArgument(name = "type") String type,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findByType(type, fields);
    }

    @Override
    @GraphQLQuery(name = "processesByIds")
    // @formatter:off
    public List<Process> findByIdIn(
        @GraphQLArgument(name = "ids") List<String> ids,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findByIdIn(ids, fields);
    }

    @Override
    @GraphQLQuery(name = "processesMostRecentlyUpdate")
    // @formatter:off
    public List<Process> findMostRecentlyUpdate(
        @GraphQLArgument(name = "limit") Integer limit,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.findMostRecentlyUpdate(limit, fields);
    }

    @Override
    public Class<Process> type() {
        return Process.class;
    }

    @Override
    protected Class<?> getOriginDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.Process.class;
    }

}
