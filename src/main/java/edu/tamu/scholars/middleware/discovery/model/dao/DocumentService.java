package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Document;
import edu.tamu.scholars.middleware.discovery.model.repo.DocumentRepo;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class DocumentService extends AbstractNestedDocumentService<Document, edu.tamu.scholars.middleware.discovery.model.Document, DocumentRepo> {

    @Override
    @GraphQLQuery(name = "documents")
    public Iterable<Document> findAll() {
        return super.findAll();
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Document.class;
    }

}
