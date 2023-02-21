package edu.tamu.scholars.middleware.export.controller;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Collection;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Collection"
})
public class CollectionExportControllerTest extends AbstractSolrDocumentExportControllerTest<Collection> {

    @Override
    protected Class<?> getType() {
        return Collection.class;
    }

}
