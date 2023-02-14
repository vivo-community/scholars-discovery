package edu.tamu.scholars.middleware.discovery;

import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.getDiscoveryDocumentTypeByName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tamu.scholars.middleware.config.SolrTestConfig;
import edu.tamu.scholars.middleware.discovery.annotation.CollectionTarget;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;

@Import(SolrTestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractSolrDocumentIntegrationTest<D extends AbstractIndexDocument> {

    @Value("classpath:mock/discovery")
    private Resource mocksDirectoryResource;

    @Autowired
    protected SolrClient solrClient;

    @Autowired
    protected IndividualRepo repo;

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
        CoreAdminRequest.Create createRequest = new CoreAdminRequest.Create();
        createRequest.setCoreName(getCollection());
        createRequest.setConfigSet(getCollection());
        solrClient.request(createRequest);
    }

    private void deleteCore() throws SolrServerException, IOException {
        CoreAdminRequest.Unload unloadRequest = new CoreAdminRequest.Unload(true);
        unloadRequest.setCoreName(getCollection());
        solrClient.request(unloadRequest);
    }

    private void createDocuments() throws IOException, SolrServerException {
         assertEquals(0, repo.count());
         DocumentObjectBinder binder = solrClient.getBinder();
         ObjectMapper objectMapper = new ObjectMapper();
         List<File> mockFiles = getMockFiles();
         for (File file : mockFiles) {
             JsonNode mockDocumentNode = objectMapper.readTree(file);
             String name = mockDocumentNode.get("class").asText();
             Class<?> type = getDiscoveryDocumentTypeByName(name);
             SolrInputDocument document = binder.toSolrInputDocument(objectMapper.convertValue(mockDocumentNode, type));
             // NOTE: the null values must be removed, until https://issues.apache.org/jira/browse/SOLR-15112 is resolved
             for (String fieldName : new ArrayList<>(document.getFieldNames())) {
                 if (document.getField(fieldName).getValue() == null) {
                     document.removeField(fieldName);
                 }
             }
             solrClient.add(getCollection(), document);
             if (type.equals(getType())) {
                 @SuppressWarnings("unchecked")
                 D mockDocument = (D) objectMapper.readValue(file, getType());
                 assertNotNull(mockDocument);
                 mockDocuments.add(mockDocument);
             }
         }
         assertTrue(mockDocuments.size() > 0, "No mock documents processed");
         solrClient.commit(getCollection());
         numberOfDocuments = (int) repo.count();
         assertEquals(mockFiles.size(), numberOfDocuments, "Indexed documents count not matching mock documents count");
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
        CollectionTarget solrDocument = getType().getAnnotation(CollectionTarget.class);
        String collection = solrDocument.name();
        assertTrue(StringUtils.isNotEmpty(collection));
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
