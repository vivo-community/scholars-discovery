package edu.tamu.scholars.middleware.export.controller;

import edu.tamu.scholars.middleware.discovery.model.Document;

public class DocumentExportControllerTest extends AbstractSolrDocumentExportControllerTest<Document> {

    @Override
    protected Class<?> getType() {
        return Document.class;
    }

}
