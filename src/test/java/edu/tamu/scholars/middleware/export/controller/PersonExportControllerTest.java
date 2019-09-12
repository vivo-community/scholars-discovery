package edu.tamu.scholars.middleware.export.controller;

import edu.tamu.scholars.middleware.discovery.model.Person;

public class PersonExportControllerTest extends AbstractSolrDocumentExportControllerTest<Person> {

    @Override
    protected Class<?> getType() {
        return Person.class;
    }

}
