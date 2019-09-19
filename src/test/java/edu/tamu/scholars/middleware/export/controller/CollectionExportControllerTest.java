package edu.tamu.scholars.middleware.export.controller;

import edu.tamu.scholars.middleware.discovery.model.Collection;

public class CollectionExportControllerTest extends AbstractSolrDocumentExportControllerTest<Collection> {

    @Override
    protected Class<?> getType() {
        return Collection.class;
    }

}
