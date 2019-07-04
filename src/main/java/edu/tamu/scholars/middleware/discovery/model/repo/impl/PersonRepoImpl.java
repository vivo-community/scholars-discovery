package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import org.springframework.data.solr.core.query.Criteria;

import edu.tamu.scholars.middleware.discovery.model.Person;

public class PersonRepoImpl extends AbstractSolrDocumentRepoImpl<Person> {

    @Override
    public Class<Person> type() {
        return Person.class;
    }

    @Override
    protected Criteria getCriteria(String query) {
        return Criteria.where("firstName").is(query).boost(2).or("lastName").is(query).boost(2).or(super.getCriteria(query));
    }
}
