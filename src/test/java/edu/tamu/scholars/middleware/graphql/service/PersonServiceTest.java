package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import edu.tamu.scholars.middleware.graphql.model.Person;
import graphql.language.Field;

public class PersonServiceTest extends AbstractNestedDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.Person, Person, PersonService> {

    @Override
    protected Class<?> getType() {
        return edu.tamu.scholars.middleware.discovery.model.Person.class;
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Person.class;
    }

    @Override
    protected List<Field> getGraphQLEnvironmentFields() {
        return new ArrayList<Field>();
    }

}
