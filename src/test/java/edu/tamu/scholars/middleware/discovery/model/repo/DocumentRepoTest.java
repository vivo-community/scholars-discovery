package edu.tamu.scholars.middleware.discovery.model.repo;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Document;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Document"
})
public class DocumentRepoTest extends AbstractSolrDocumentRepoTest<Document> {

    @Override
    protected Class<?> getType() {
        return Document.class;
    }

}
