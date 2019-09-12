package edu.tamu.scholars.middleware.discovery.model.repo;

import edu.tamu.scholars.middleware.discovery.model.Concept;

public class ConceptRepoTest extends AbstractSolrDocumentRepoTest<Concept> {

    @Override
    protected Class<?> getType() {
        return Concept.class;
    }

}
