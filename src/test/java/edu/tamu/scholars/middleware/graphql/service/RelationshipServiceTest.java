package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import edu.tamu.scholars.middleware.graphql.model.Relationship;
import graphql.language.Field;

public class RelationshipServiceTest extends AbstractNestedDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.Relationship, Relationship, RelationshipService> {

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
