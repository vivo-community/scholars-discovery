package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import org.springframework.data.solr.core.query.Criteria;

import edu.tamu.scholars.middleware.discovery.model.Document;

public class DocumentRepoImpl extends AbstractSolrDocumentRepoImpl<Document> {

    @Override
    public Class<Document> type() {
        return Document.class;
    }

    @Override
    protected Criteria getCriteria(String query) {
        return Criteria.where("title").is(query).boost(2).or("keywords").is(query).boost(2).or(super.getCriteria(query));
    }

}
