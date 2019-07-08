package edu.tamu.scholars.middleware.graphql.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.repo.DocumentRepo;
import edu.tamu.scholars.middleware.graphql.model.Document;

public class DocumentServiceTest extends AbstractNestedDocumentServiceTest<Document, edu.tamu.scholars.middleware.discovery.model.Document, DocumentRepo, DocumentService> {

    @Value("classpath:mock/discovery/document")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return edu.tamu.scholars.middleware.discovery.model.Document.class;
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Document.class;
    }

}
