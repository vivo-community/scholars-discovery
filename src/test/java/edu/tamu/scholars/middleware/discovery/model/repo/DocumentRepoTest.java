package edu.tamu.scholars.middleware.discovery.model.repo;

import edu.tamu.scholars.middleware.discovery.model.Document;

public class DocumentRepoTest extends AbstractSolrDocumentRepoTest<Document> {

    @Override
    protected Class<?> getType() {
        return Document.class;
    }

}
