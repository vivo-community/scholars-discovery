package edu.tamu.scholars.middleware.discovery.model.repo;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Relationship;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Relationship"
})
public class RelationshipRepoTest extends AbstractSolrDocumentRepoTest<Relationship> {

    @Override
    protected Class<?> getType() {
        return Relationship.class;
    }

}
