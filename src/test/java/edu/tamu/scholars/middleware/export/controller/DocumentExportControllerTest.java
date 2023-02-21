package edu.tamu.scholars.middleware.export.controller;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Document;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Document"
})
public class DocumentExportControllerTest extends AbstractSolrDocumentExportControllerTest<Document> {

    @Override
    protected Class<?> getType() {
        return Document.class;
    }

}
