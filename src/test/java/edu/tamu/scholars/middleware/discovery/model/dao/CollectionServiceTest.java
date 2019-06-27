package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Collection;
import edu.tamu.scholars.middleware.discovery.model.doa.CollectionService;
import edu.tamu.scholars.middleware.discovery.model.repo.CollectionRepo;

public class CollectionServiceTest extends AbstractSolrDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.generated.collection.Collection, Collection, CollectionRepo, CollectionService> {

    @Value("classpath:mock/discovery/collection")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Collection.class;
    }

}
