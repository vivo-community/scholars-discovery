package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Document;

public class DocumentControllerTest extends AbstractSolrDocumentControllerTest<Document> {

    @Value("classpath:mock/discovery/documents")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Document.class;
    }

    @Override
    protected String getPath() {
        return "/individual";
    }

}
