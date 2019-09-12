package edu.tamu.scholars.middleware.export.controller;

import edu.tamu.scholars.middleware.discovery.model.Concept;

public class ConceptExportControllerTest extends AbstractSolrDocumentExportControllerTest<Concept> {

    @Override
    protected Class<?> getType() {
        return Concept.class;
    }

}
