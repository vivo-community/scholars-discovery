package edu.tamu.scholars.middleware.export.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Relationship;
import edu.tamu.scholars.middleware.discovery.model.repo.RelationshipRepo;

public class RelationshipExportControllerTest extends AbstractSolrDocumentExportControllerTest<Relationship, RelationshipRepo> {

    @Value("classpath:mock/discovery/relationship")
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
        return "/relationships";
    }

}
