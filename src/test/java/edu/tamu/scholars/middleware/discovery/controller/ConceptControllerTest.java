package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Concept;

public class ConceptControllerTest extends AbstractSolrDocumentControllerTest<Concept> {

    @Value("classpath:mock/discovery/concepts")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Concept.class;
    }

    @Override
    protected String getPath() {
        return "/individuals";
    }

}
