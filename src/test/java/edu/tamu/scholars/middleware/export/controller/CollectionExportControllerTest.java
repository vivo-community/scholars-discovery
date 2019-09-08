package edu.tamu.scholars.middleware.export.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Collection;

public class CollectionExportControllerTest extends AbstractSolrDocumentExportControllerTest<Collection> {

    @Value("classpath:mock/discovery/collections")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Collection.class;
    }

    @Override
    protected String getPath() {
        return "/individuals";
    }

}
