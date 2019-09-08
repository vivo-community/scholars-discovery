package edu.tamu.scholars.middleware.export.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Person;

public class PersonExportControllerTest extends AbstractSolrDocumentExportControllerTest<Person> {

    @Value("classpath:mock/discovery/persons")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Person.class;
    }

    @Override
    protected String getPath() {
        return "/individuals";
    }

}
