package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Relationship;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Relationship"
})
public class RelationshipControllerTest extends AbstractSolrDocumentControllerTest<Relationship> {

    @Override
    protected Class<?> getType() {
        return Relationship.class;
    }

}
