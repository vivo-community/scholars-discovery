package edu.tamu.scholars.middleware.discovery.model.repo;

import edu.tamu.scholars.middleware.discovery.model.Collection;

public class CollectionRepoTest extends AbstractSolrDocumentRepoTest<Collection> {

    @Override
    protected Class<?> getType() {
        return Collection.class;
    }

}
