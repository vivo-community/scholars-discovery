package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Collection;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Collection"
})
public class CollectionControllerTest extends AbstractSolrDocumentControllerTest<Collection> {

    @Override
    protected Class<?> getType() {
        return Collection.class;
    }

}
