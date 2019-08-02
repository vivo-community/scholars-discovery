package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.argument.Facet;
import edu.tamu.scholars.middleware.discovery.argument.Filter;
import edu.tamu.scholars.middleware.discovery.argument.Index;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;
import edu.tamu.scholars.middleware.graphql.model.Person;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class PersonService extends AbstractNestedDocumentService<Person, edu.tamu.scholars.middleware.discovery.model.Person, PersonRepo> {

    // TODO: figure out how to use findById returning Optional
    // TODO: figure out how to use name persons
    @Override
    @GraphQLQuery(name = "person")
    public Person getById(@GraphQLArgument(name = "id") String id) {
        return super.getById(id);
    }

    @Override
    @GraphQLQuery(name = "personCount")
    public long count() {
        return super.count();
    }

    @Override
    @GraphQLQuery(name = "personCount")
    // @formatter:off
    public long count(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<Filter> filters
    ) {
    // @formatter:on 
        return super.count(query, filters);
    }

    @Override
    @GraphQLQuery(name = "personExists")
    public boolean existsById(String id) {
        return super.existsById(id);
    }

    @Override
    @GraphQLQuery(name = "persons")
    public Iterable<Person> findAll() {
        return super.findAll();
    }

    @Override
    @GraphQLQuery(name = "persons")
    public Iterable<Person> findAll(@GraphQLArgument(name = "sort") Sort sort) {
        return super.findAll(sort);
    }

    @Override
    @GraphQLQuery(name = "persons")
    public Page<Person> findAll(@GraphQLArgument(name = "paging") Pageable pageable) {
        return super.findAll(pageable);
    }

    @GraphQLQuery(name = "persons")
    // @formatter:off
    public FacetPage<Person> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return search(query, Optional.empty(), new ArrayList<Facet>(), new ArrayList<Filter>(), page);
    }

    @GraphQLQuery(name = "persons")
    // @formatter:off
    public FacetPage<Person> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "index") Optional<Index> index,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return search(query, index, new ArrayList<Facet>(), new ArrayList<Filter>(), page);
    }

    @GraphQLQuery(name = "persons")
    // @formatter:off
    public FacetPage<Person> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "filters") List<Filter> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return search(query, Optional.empty(), new ArrayList<Facet>(), filters, page);
    }

    @GraphQLQuery(name = "persons")
    // @formatter:off
    public FacetPage<Person> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "facets") List<Facet> facets,
        @GraphQLArgument(name = "filters") List<Filter> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return search(query, Optional.empty(), facets, filters, page);
    }

    @Override
    @GraphQLQuery(name = "persons")
    // @formatter:off
    public FacetPage<Person> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "index") Optional<Index> index,
        @GraphQLArgument(name = "facets") List<Facet> facets,
        @GraphQLArgument(name = "filters") List<Filter> filters,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.search(query, index, facets, filters, page);
    }

    @Override
    @GraphQLQuery(name = "persons")
    public List<Person> findByType(@GraphQLArgument(name = "type") String type) {
        return super.findByType(type);
    }

    @Override
    @GraphQLQuery(name = "persons")
    public List<Person> findMostRecentlyUpdate(@GraphQLArgument(name = "limit") Integer limit) {
        return super.findMostRecentlyUpdate(limit);
    }

    @Override
    public Class<Person> type() {
        return Person.class;
    }

}
