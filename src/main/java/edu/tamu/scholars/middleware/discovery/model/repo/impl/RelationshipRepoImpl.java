package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import edu.tamu.scholars.middleware.discovery.model.Relationship;

public class RelationshipRepoImpl extends AbstractSolrDocumentRepoImpl<Relationship> {

    @Override
    public Class<Relationship> type() {
        return Relationship.class;
    }

}
