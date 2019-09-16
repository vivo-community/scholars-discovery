package edu.tamu.scholars.middleware.discovery.controller;

import edu.tamu.scholars.middleware.discovery.model.Document;

public class DocumentControllerTest extends AbstractSolrDocumentControllerTest<Document> {

    @Override
    protected Class<?> getType() {
        return Document.class;
    }

}
