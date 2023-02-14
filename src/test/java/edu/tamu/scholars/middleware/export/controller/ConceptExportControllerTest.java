package edu.tamu.scholars.middleware.export.controller;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Concept;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Concept"
})
public class ConceptExportControllerTest extends AbstractSolrDocumentExportControllerTest<Concept> {

    @Override
    protected Class<?> getType() {
        return Concept.class;
    }

}
