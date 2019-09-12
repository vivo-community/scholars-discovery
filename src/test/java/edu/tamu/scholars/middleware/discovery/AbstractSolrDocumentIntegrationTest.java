package edu.tamu.scholars.middleware.discovery;

import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.getDiscoveryDocumentTypeByName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.core.query.SimpleQuery;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractSolrDocumentIntegrationTest<D extends AbstractSolrDocument> {

    @Value("classpath:solr/discovery")
    private Resource instanceDirectory;

    @Value("classpath:mock/discovery")
    private Resource mocksDirectoryResource;

    @Autowired
    private EmbeddedSolrServer solrServer;

    @Autowired
    protected IndividualRepo repo;

    @Autowired
    protected SolrTemplate solrTemplate;

    protected List<D> mockDocuments = new ArrayList<D>();

    protected int numberOfDocuments = 0;

    @BeforeAll
    public void setup() throws SolrServerException, IOException {
        createCore();
        createDocuments();
    }

    @AfterAll
    public void cleanup() throws SolrServerException, IOException {
        deleteDocuments();
        deleteCore();
    }

    private void createCore() throws SolrServerException, IOException {
        assertTrue(instanceDirectory.exists());
        assertTrue(instanceDirectory.isFile());
        CoreAdminRequest.createCore(getCollection(), instanceDirectory.getFile().getAbsolutePath(), solrServer);
    }

    private void deleteCore() throws SolrServerException, IOException {
        CoreAdminRequest.unloadCore(getCollection(), solrServer);
    }

    private void createDocuments() throws IOException {
        assertEquals(0, repo.count());
        ObjectMapper objectMapper = new ObjectMapper();
        List<File> mockFiles = getMockFiles();
        for (File file : mockFiles) {
            JsonNode mockDocumentNode = objectMapper.readTree(file);
            String name = mockDocumentNode.get("class").asText();
            Class<?> type = getDiscoveryDocumentTypeByName(name);
            solrTemplate.saveBean(getCollection(), objectMapper.readValue(file, type));
            if (type.equals(getType())) {
                @SuppressWarnings("unchecked")
                D mockDocument = (D) objectMapper.readValue(file, getType());
                assertNotNull(mockDocument);
                mockDocuments.add(mockDocument);
            }
        }
        assertTrue(mockDocuments.size() > 0);
        solrTemplate.commit(getCollection());
        numberOfDocuments = (int) solrTemplate.count(getCollection(), new SimpleQuery("*"));
        assertEquals(mockFiles.size(), numberOfDocuments);
    }

    private void deleteDocuments() {
        repo.deleteAll();
    }

    private List<File> getMockFiles() throws IOException {
        assertTrue(mocksDirectoryResource.exists());
        assertTrue(mocksDirectoryResource.isFile());
        File mocksDirectory = mocksDirectoryResource.getFile();
        assertTrue(mocksDirectory.isDirectory());
        return Files.walk(mocksDirectory.toPath(), 2).map(path -> path.toFile()).filter(file -> file.isFile()).collect(Collectors.toList());
    }

    private String getCollection() {
        SolrDocument solrDocument = getType().getAnnotation(SolrDocument.class);
        String collection = solrDocument.collection();
        assertFalse(collection.isEmpty());
        return collection;
    }

    protected String getDocPath() {
        String docPath = getType().getSimpleName().toLowerCase();
        if (docPath.equals("process")) {
            docPath += "es";
        } else {
            docPath += "s";
        }
        return docPath;
    }

    protected abstract Class<?> getType();

}
