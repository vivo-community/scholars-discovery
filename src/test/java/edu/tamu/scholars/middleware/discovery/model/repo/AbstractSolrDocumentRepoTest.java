package edu.tamu.scholars.middleware.discovery.model.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Test
    public void testFindAll() throws IOException {
        List<Individual> documents = StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
        assertEquals(numberOfDocuments, documents.size());
    }

}
