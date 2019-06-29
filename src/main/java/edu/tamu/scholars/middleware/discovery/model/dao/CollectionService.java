package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Collection;
import edu.tamu.scholars.middleware.discovery.model.repo.CollectionRepo;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class CollectionService extends AbstractNestedDocumentService<Collection, edu.tamu.scholars.middleware.discovery.model.Collection, CollectionRepo> {

    @Override
    @GraphQLQuery(name = "collections")
    public Iterable<Collection> findAll() {
        return super.findAll();
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Collection.class;
    }

}
