package edu.tamu.scholars.middleware.discovery.model.doa;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Relationship;
import edu.tamu.scholars.middleware.discovery.model.repo.RelationshipRepo;

@Service
public class RelationshipService extends AbstractNestedDocumentService<edu.tamu.scholars.middleware.discovery.model.generated.Relationship, Relationship, RelationshipRepo> {

    @Override
    protected Class<?> getNestedDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.generated.Relationship.class;
    }

}
