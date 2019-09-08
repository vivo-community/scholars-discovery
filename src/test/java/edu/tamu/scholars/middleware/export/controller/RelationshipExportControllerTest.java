package edu.tamu.scholars.middleware.export.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Relationship;

public class RelationshipExportControllerTest extends AbstractSolrDocumentExportControllerTest<Relationship> {

    @Value("classpath:mock/discovery/relationships")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Relationship.class;
    }

    @Override
    protected String getPath() {
        return "/individuals";
    }

}
