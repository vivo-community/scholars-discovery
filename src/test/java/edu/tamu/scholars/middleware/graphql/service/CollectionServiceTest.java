package edu.tamu.scholars.middleware.graphql.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.repo.CollectionRepo;
import edu.tamu.scholars.middleware.graphql.model.Collection;

public class CollectionServiceTest extends AbstractNestedDocumentServiceTest<Collection, edu.tamu.scholars.middleware.discovery.model.Collection, CollectionRepo, CollectionService> {

    @Value("classpath:mock/discovery/collections")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return edu.tamu.scholars.middleware.discovery.model.Collection.class;
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Collection.class;
    }

}
