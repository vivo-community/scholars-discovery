package edu.tamu.scholars.middleware.discovery.controller;

import edu.tamu.scholars.middleware.discovery.model.Relationship;

public class RelationshipControllerTest extends AbstractSolrDocumentControllerTest<Relationship> {

    @Override
    protected Class<?> getType() {
        return Relationship.class;
    }

}
