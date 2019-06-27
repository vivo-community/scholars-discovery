package edu.tamu.scholars.middleware.discovery.model.doa;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Collection;
import edu.tamu.scholars.middleware.discovery.model.repo.CollectionRepo;

@Service
public class CollectionService extends AbstractSolrDocumentService<edu.tamu.scholars.middleware.discovery.model.generated.collection.Collection, Collection, CollectionRepo> {

    @Override
    protected Class<?> getNestedDocumentClass() {
        return edu.tamu.scholars.middleware.discovery.model.generated.collection.Collection.class;
    }

}
