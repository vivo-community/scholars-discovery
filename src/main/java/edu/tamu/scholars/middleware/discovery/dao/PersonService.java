package edu.tamu.scholars.middleware.discovery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Person;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class PersonService extends AbstractNestedDocumentService<Person, edu.tamu.scholars.middleware.discovery.model.Person, PersonRepo> {

    // TODO: figure out how to use findById returning Optional
    // TODO: figure out how to use name collections
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
    public long count(String query, String[] fields, Map<String, List<String>> params) {
        return super.count(query, fields, params);
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

    @Override
    @GraphQLQuery(name = "persons")
    // @formatter:off
    public FacetPage<Person> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "index") String index,
        @GraphQLArgument(name = "facets") String[] facets,
        @GraphQLArgument(name = "params") Map<String, List<String>> params,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.search(query, index, facets, params, page);
    }

    @Override
    @GraphQLQuery(name = "persons")
    public List<Person> findByType(@GraphQLArgument(name = "type") String type) {
        return super.findByType(type);
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Person.class;
    }

}
