package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import org.springframework.data.solr.core.query.Criteria;

import edu.tamu.scholars.middleware.discovery.model.Relationship;

public class RelationshipRepoImpl extends AbstractSolrDocumentRepoImpl<Relationship> {

    @Override
    public Class<Relationship> type() {
        return Relationship.class;
    }

    @Override
    protected Criteria getCriteria(String query) {
        return Criteria.where("title").is(query).boost(2).or("awardedBy").is(query).boost(2).or(super.getCriteria(query));
    }
}
