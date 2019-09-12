package edu.tamu.scholars.middleware.export.controller;

import edu.tamu.scholars.middleware.discovery.model.Relationship;

public class RelationshipExportControllerTest extends AbstractSolrDocumentExportControllerTest<Relationship> {

    @Override
    protected Class<?> getType() {
        return Relationship.class;
    }

}
