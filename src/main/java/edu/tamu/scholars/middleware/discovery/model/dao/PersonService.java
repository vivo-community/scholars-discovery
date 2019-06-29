package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Person;
import edu.tamu.scholars.middleware.discovery.model.repo.PersonRepo;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class PersonService extends AbstractNestedDocumentService<Person, edu.tamu.scholars.middleware.discovery.model.Person, PersonRepo> {

    @Override
    @GraphQLQuery(name = "persons")
    public Iterable<Person> findAll() {
        return super.findAll();
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Person.class;
    }

}
