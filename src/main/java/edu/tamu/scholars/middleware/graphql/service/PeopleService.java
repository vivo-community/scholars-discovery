package edu.tamu.scholars.middleware.graphql.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;
import edu.tamu.scholars.middleware.graphql.model.Person;
import edu.tamu.scholars.middleware.graphql.type.GraphQLFacetPage;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class PeopleService extends AbstractNestedDocumentService<Person, edu.tamu.scholars.middleware.discovery.model.Person, PersonRepo> {

    private List<FacetArg> defaultFacetList = Collections.<FacetArg> emptyList();
    private List<FilterArg> defaultFilterList = Collections.<FilterArg> emptyList();
    private Pageable defaultPageRequest = PageRequest.of(0,10); // TODO: read values from configuration

    // TODO: make deserializer for default values to allow: @GraphQLArgument(name = "paging", defaultValue = "\"{\"pageNumber\": \"0\", \"pageSize\":\"10\"}\"") Pageable page
    @GraphQLQuery(name = "people")
    // @formatter:off
    public GraphQLFacetPage<Person> people(
        @GraphQLArgument(name = "query", defaultValue = "*:*") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "filters") List<FilterArg> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, page);
    }

    @GraphQLQuery(name = "people")
    // @formatter:off
    public GraphQLFacetPage<Person> people(
        @GraphQLArgument(name = "query", defaultValue = "*:*") String query
    ) {
    // @formatter:on
        return super.facetedSearch(query, defaultFacetList, defaultFilterList, defaultPageRequest);
    }

    @GraphQLQuery(name = "people")
    // @formatter:off
    public GraphQLFacetPage<Person> people(
        @GraphQLArgument(name = "query", defaultValue = "*:*") String query,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, defaultFacetList, defaultFilterList, page);
    }

    @GraphQLQuery(name = "people")
    // @formatter:off
    public GraphQLFacetPage<Person> people(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<FacetArg> facets,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, defaultFilterList, page);
    }

    @GraphQLQuery(name = "people")
    // @formatter:off
    public GraphQLFacetPage<Person> people(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "paging") Pageable page,
        @GraphQLArgument(name = "filters") List<FilterArg> filters
    ) {
    // @formatter:on
        return super.facetedSearch(query, defaultFacetList, filters, page);
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

