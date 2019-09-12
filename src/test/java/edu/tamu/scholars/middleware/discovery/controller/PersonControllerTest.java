package edu.tamu.scholars.middleware.discovery.controller;

import edu.tamu.scholars.middleware.discovery.model.Person;

public class PersonControllerTest extends AbstractSolrDocumentControllerTest<Person> {

    @Override
    protected Class<?> getType() {
        return Person.class;
    }

}
