package edu.tamu.scholars.middleware.discovery.model.doa;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Concept;
import edu.tamu.scholars.middleware.discovery.model.repo.ConceptRepo;

@Service
public class ConceptService extends AbstractSolrDocumentService<edu.tamu.scholars.middleware.discovery.model.generated.Concept, Concept, ConceptRepo> {

    @Override
    protected Class<?> getNestedDocumentClass() {
        return edu.tamu.scholars.middleware.discovery.model.generated.Concept.class;
    }

}
