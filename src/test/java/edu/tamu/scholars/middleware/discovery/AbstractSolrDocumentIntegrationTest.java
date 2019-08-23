package edu.tamu.scholars.middleware.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractSolrDocumentIntegrationTest<D extends AbstractSolrDocument, R extends SolrDocumentRepo<D>> {

    @Value("classpath:solr/discovery")
    private Resource instanceDirectory;

    @Autowired
    private EmbeddedSolrServer solrServer;

    @Autowired
    protected R repo;

    protected List<D> mockDocuments;

    @BeforeAll
    public void setup() throws IOException {
        createCore(getCollection());
        setDocuments();
        createDocuments();
    }

    @AfterAll
    public void cleanup() throws IOException {
        deleteDocuments();
        deleteCore(getCollection());
    }

    protected void createCore(String collection) {
        assertTrue(instanceDirectory.exists());
        assertTrue(instanceDirectory.isFile());
        try {
            File src = instanceDirectory.getFile();
            File dest = new File(String.format("%s%s%s", src.getParentFile().getAbsolutePath(), File.separator, collection));
            FileUtils.copyDirectory(src, dest);
            CoreAdminRequest.createCore(collection, dest.getAbsolutePath(), solrServer);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    protected void deleteCore(String collection) {
        try {
            CoreAdminRequest.unloadCore(collection, true, solrServer);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setDocuments() throws IOException {
        mockDocuments = new ArrayList<D>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (File file : getMockFiles()) {
            if (file.isFile()) {
                @SuppressWarnings("unchecked")
                D mockDocument = (D) objectMapper.readValue(file, getType());
                assertNotNull(mockDocument);
                mockDocuments.add(mockDocument);
            }
        }
        assertTrue(mockDocuments.size() > 0);
    }

    private void createDocuments() {
        assertEquals(0, repo.count());
        mockDocuments.forEach(mockDocument -> {
            repo.save(mockDocument);
        });
        assertEquals(mockDocuments.size(), repo.count());
    }

    private void deleteDocuments() {
        repo.deleteAll();
    }

    private File[] getMockFiles() throws IOException {
        Resource mocksDirectoryResource = getMocksDirectory();
        assertTrue(mocksDirectoryResource.exists());
        assertTrue(mocksDirectoryResource.isFile());
        File mocksDirectory = mocksDirectoryResource.getFile();
        assertTrue(mocksDirectory.isDirectory());
        return mocksDirectory.listFiles();
    }

    private String getCollection() {
        SolrDocument solrDocument = getType().getAnnotation(SolrDocument.class);
        String collection = solrDocument.collection();
        assertFalse(collection.isEmpty());
        return collection;
    }

    protected abstract Resource getMocksDirectory();

    protected abstract Class<?> getType();

}
