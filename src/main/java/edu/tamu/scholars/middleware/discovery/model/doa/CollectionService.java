package edu.tamu.scholars.middleware.discovery.model.doa;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Collection;
import edu.tamu.scholars.middleware.discovery.model.repo.CollectionRepo;

@Service
public class CollectionService extends AbstractNestedDocumentService<edu.tamu.scholars.middleware.discovery.model.generated.Collection, Collection, CollectionRepo> {

    @Override
    protected Class<?> getNestedDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.generated.Collection.class;
    }

}
