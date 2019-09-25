package edu.tamu.scholars.middleware.discovery.service;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Concept;

@Service
public class ConceptIndexService extends AbstractSolrIndexService<Concept> {

    @Override
    public Class<?> type() {
        return Concept.class;
    }

}
