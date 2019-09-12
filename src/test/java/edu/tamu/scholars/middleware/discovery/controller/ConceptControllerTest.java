package edu.tamu.scholars.middleware.discovery.controller;

import edu.tamu.scholars.middleware.discovery.model.Concept;

public class ConceptControllerTest extends AbstractSolrDocumentControllerTest<Concept> {

    @Override
    protected Class<?> getType() {
        return Concept.class;
    }

}
