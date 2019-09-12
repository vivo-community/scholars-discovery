package edu.tamu.scholars.middleware.discovery.controller;

import edu.tamu.scholars.middleware.discovery.model.Collection;

public class CollectionControllerTest extends AbstractSolrDocumentControllerTest<Collection> {

    @Override
    protected Class<?> getType() {
        return Collection.class;
    }

}
