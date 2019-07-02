package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import org.springframework.data.solr.core.query.Criteria;

import edu.tamu.scholars.middleware.discovery.model.Process;

public class ProcessRepoImpl extends AbstractSolrDocumentRepoImpl<Process> {

    @Override
    public String collection() {
        return "processes";
    }

    @Override
    public Class<Process> type() {
        return Process.class;
    }

    @Override
    protected Criteria getCriteria(String query) {
        return Criteria.where("title").is(query).boost(2).or(super.getCriteria(query));
    }

}
