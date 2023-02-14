package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Concept;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Concept"
})
public class ConceptControllerTest extends AbstractSolrDocumentControllerTest<Concept> {

    @Override
    protected Class<?> getType() {
        return Concept.class;
    }

}
