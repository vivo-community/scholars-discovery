package edu.tamu.scholars.middleware.discovery.service;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Document;

@Service
public class DocumentIndexService extends AbstractSolrIndexService<Document> {

    @Override
    public Class<?> type() {
        return Document.class;
    }

}
