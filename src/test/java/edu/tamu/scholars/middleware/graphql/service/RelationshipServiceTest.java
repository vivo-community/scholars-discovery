package edu.tamu.scholars.middleware.graphql.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.repo.RelationshipRepo;
import edu.tamu.scholars.middleware.graphql.model.Relationship;

public class RelationshipServiceTest extends AbstractNestedDocumentServiceTest<Relationship, edu.tamu.scholars.middleware.discovery.model.Relationship, RelationshipRepo, RelationshipService> {

    @Value("classpath:mock/discovery/relationships")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return edu.tamu.scholars.middleware.discovery.model.Relationship.class;
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Relationship.class;
    }

}
