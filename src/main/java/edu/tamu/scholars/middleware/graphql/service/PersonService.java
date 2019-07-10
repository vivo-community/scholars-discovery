package edu.tamu.scholars.middleware.graphql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.IndexArg;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;
import edu.tamu.scholars.middleware.graphql.model.Person;
import edu.tamu.scholars.middleware.graphql.type.GraphQLFacetPage;
import edu.tamu.scholars.middleware.graphql.type.GraphQLPage;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class PersonService extends AbstractNestedDocumentService<Person, edu.tamu.scholars.middleware.discovery.model.Person, PersonRepo> {

    @Override
    @GraphQLQuery(name = "personExistsById")
    public boolean existsById(@GraphQLArgument(name = "id") String id) {
        return super.existsById(id);
    }

    @Override
    @GraphQLQuery(name = "personById")
    public Person getById(@GraphQLArgument(name = "id") String id) {
        return super.getById(id);
    }

    @Override
    @GraphQLQuery(name = "personsCount")
    public long count() {
        return super.count();
    }

    @Override
    @GraphQLQuery(name = "personsCount")
    // @formatter:off
    public long count(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters
    ) {
    // @formatter:on
        return super.count(query, filters);
    }

    @Override
    @GraphQLQuery(name = "personsSorted")
    public Iterable<Person> findAll(@GraphQLArgument(name = "sort") Sort sort) {
        return super.findAll(sort);
    }

    @Override
    @GraphQLQuery(name = "personsPaged")
    public GraphQLPage<Person> findAllPaged(@GraphQLArgument(name = "paging") Pageable page) {
        return super.findAllPaged(page);
    }

    @Override
    @GraphQLQuery(name = "personsSearch")
    // @formatter:off
    public GraphQLFacetPage<Person> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.search(query, page);
    }

    @Override
    @GraphQLQuery(name = "personsFilterSearch")
    // @formatter:off
    public GraphQLFacetPage<Person> filterSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.filterSearch(query, filters, page);
    }

    @Override
    @GraphQLQuery(name = "personsFacetedSearch")
    // @formatter:off
    public GraphQLFacetPage<Person> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, page);
    }

    @Override
    @GraphQLQuery(name = "personsFacetedSearch")
    // @formatter:off
    public GraphQLFacetPage<Person> facetedSearch(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, page);
    }

    @Override
    @GraphQLQuery(name = "personsFacetedSearchIndex")
    // @formatter:off
    public GraphQLFacetPage<Person> facetedSearch(
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
    @GraphQLQuery(name = "personsByType")
    public List<Person> findByType(@GraphQLArgument(name = "type") String type) {
        return super.findByType(type);
    }

    @Override
    @GraphQLQuery(name = "personsByIds")
    public List<Person> findByIdIn(@GraphQLArgument(name = "ids") List<String> ids) {
        return super.findByIdIn(ids);
    }

    @Override
    @GraphQLQuery(name = "personsMostRecentlyUpdate")
    public List<Person> findMostRecentlyUpdate(@GraphQLArgument(name = "limit") Integer limit) {
        return super.findMostRecentlyUpdate(limit);
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Person.class;
    }

    @Override
    protected Class<?> getOriginDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.Person.class;
    }

}
