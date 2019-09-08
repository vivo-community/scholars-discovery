package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.graphql.model.Person;
import graphql.language.Field;

public class PersonServiceTest extends AbstractNestedDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.Person, Person, PersonService> {

    @Value("classpath:mock/discovery/persons")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

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
