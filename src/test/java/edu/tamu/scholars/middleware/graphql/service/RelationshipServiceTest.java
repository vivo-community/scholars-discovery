package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.graphql.model.Relationship;
import graphql.language.Field;

public class RelationshipServiceTest extends AbstractNestedDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.Relationship, Relationship, RelationshipService> {

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

    @Override
    protected List<Field> getGraphQLEnvironmentFields() {
        return new ArrayList<Field>();
    }

}
