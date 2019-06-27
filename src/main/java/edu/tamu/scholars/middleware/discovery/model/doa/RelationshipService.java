package edu.tamu.scholars.middleware.discovery.model.doa;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Relationship;
import edu.tamu.scholars.middleware.discovery.model.repo.RelationshipRepo;

@Service
public class RelationshipService extends AbstractSolrDocumentService<edu.tamu.scholars.middleware.discovery.model.generated.Relationship, Relationship, RelationshipRepo> {

    @Override
    protected Class<?> getNestedDocumentClass() {
        return edu.tamu.scholars.middleware.discovery.model.generated.Relationship.class;
    }

}
