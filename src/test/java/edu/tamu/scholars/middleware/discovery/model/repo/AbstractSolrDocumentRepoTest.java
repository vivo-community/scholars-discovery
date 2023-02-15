package edu.tamu.scholars.middleware.discovery.model.repo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.tamu.scholars.middleware.discovery.AbstractSolrDocumentIntegrationTest;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.model.Individual;

@SpringBootTest
public abstract class AbstractSolrDocumentRepoTest<D extends AbstractIndexDocument> extends AbstractSolrDocumentIntegrationTest<D> {

    @Test
    public void testCreate() {
        // NOTE: create is tested before all tests
        assertTrue(true);
    }

    @Test
    public void testFindById() throws IOException {
        mockDocuments.forEach(mockDocument -> {
            String id = mockDocument.getId();
            Optional<Individual> document = repo.findById(id);
            assertTrue(document.isPresent());
        });
    }

}
