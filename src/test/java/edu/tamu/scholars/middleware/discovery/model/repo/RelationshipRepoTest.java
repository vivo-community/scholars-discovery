package edu.tamu.scholars.middleware.discovery.model.repo;

import edu.tamu.scholars.middleware.discovery.model.Relationship;

public class RelationshipRepoTest extends AbstractSolrDocumentRepoTest<Relationship> {

    @Override
    protected Class<?> getType() {
        return Relationship.class;
    }

}
