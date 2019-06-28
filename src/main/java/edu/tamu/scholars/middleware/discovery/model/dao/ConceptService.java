package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Concept;
import edu.tamu.scholars.middleware.discovery.model.repo.ConceptRepo;

@Service
public class ConceptService extends AbstractNestedDocumentService<edu.tamu.scholars.middleware.discovery.model.generated.Concept, Concept, ConceptRepo> {

    @Override
    protected Class<?> getNestedDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.generated.Concept.class;
    }

}
