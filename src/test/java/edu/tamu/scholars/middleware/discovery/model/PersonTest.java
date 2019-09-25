package edu.tamu.scholars.middleware.discovery.model;

public class PersonTest extends AbstractIndexDocumentTest<Person> {

    @Override
    protected Class<?> getType() {
        return Person.class;
    }

}
