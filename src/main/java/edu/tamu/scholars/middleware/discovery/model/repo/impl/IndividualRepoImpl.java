package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.solr.core.query.SimpleFilterQuery;

import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.model.Individual;

public class IndividualRepoImpl extends AbstractSolrDocumentRepoImpl<Individual> {

    @Override
    protected List<SimpleFilterQuery> buildFilterQueries(List<FilterArg> filters) {
        return filters.stream().map(filter -> new SimpleFilterQuery(buildCriteria(filter))).collect(Collectors.toList());
    }

    @Override
    public Class<Individual> type() {
        return Individual.class;
    }

}
