package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import org.springframework.data.solr.core.query.Criteria;

import edu.tamu.scholars.middleware.discovery.model.Concept;

public class ConceptRepoImpl extends AbstractSolrDocumentRepoImpl<Concept> {

    @Override
    public Class<Concept> type() {
        return Concept.class;
    }

    @Override
    protected Criteria getCriteria(String query) {
        return Criteria.where("name").is(query).boost(2).or(super.getCriteria(query));
    }
}
