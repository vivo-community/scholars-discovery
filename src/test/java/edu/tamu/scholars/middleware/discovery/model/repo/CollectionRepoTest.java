package edu.tamu.scholars.middleware.discovery.model.repo;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Collection;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Collection"
})
public class CollectionRepoTest extends AbstractSolrDocumentRepoTest<Collection> {

    @Override
    protected Class<?> getType() {
        return Collection.class;
    }

}
