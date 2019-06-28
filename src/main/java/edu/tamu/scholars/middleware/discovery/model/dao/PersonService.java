package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Person;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;

import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.annotations.GraphQLQuery;

@GraphQLApi
@Service
public class PersonService extends AbstractNestedDocumentService<edu.tamu.scholars.middleware.discovery.model.generated.Person, Person, PersonRepo> {

    @GraphQLQuery(name = "people")
    public Iterable<edu.tamu.scholars.middleware.discovery.model.generated.Person> getPeople() {
        return this.findAll();
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.generated.Person.class;
    }

}
