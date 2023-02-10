package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Document;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Document"
})
public class DocumentControllerTest extends AbstractSolrDocumentControllerTest<Document> {

    @Override
    protected Class<?> getType() {
        return Document.class;
    }

}
