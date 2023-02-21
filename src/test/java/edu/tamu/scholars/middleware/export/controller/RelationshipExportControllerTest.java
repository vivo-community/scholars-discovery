package edu.tamu.scholars.middleware.export.controller;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Relationship;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Relationship"
})
public class RelationshipExportControllerTest extends AbstractSolrDocumentExportControllerTest<Relationship> {

    @Override
    protected Class<?> getType() {
        return Relationship.class;
    }

}
