package edu.tamu.scholars.middleware.discovery.model.repo;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Concept;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Concept"
})
public class ConceptRepoTest extends AbstractSolrDocumentRepoTest<Concept> {

    @Override
    protected Class<?> getType() {
        return Concept.class;
    }

}
