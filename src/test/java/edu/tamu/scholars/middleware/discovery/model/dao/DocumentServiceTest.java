package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Document;
import edu.tamu.scholars.middleware.discovery.model.doa.DocumentService;
import edu.tamu.scholars.middleware.discovery.model.repo.DocumentRepo;

public class DocumentServiceTest extends AbstractSolrDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.generated.Document, Document, DocumentRepo, DocumentService> {

    @Value("classpath:mock/discovery/document")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Document.class;
    }

}
