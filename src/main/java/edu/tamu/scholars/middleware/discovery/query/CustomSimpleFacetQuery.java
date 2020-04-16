package edu.tamu.scholars.middleware.discovery.query;

import org.springframework.data.solr.core.query.SimpleFacetQuery;

public class CustomSimpleFacetQuery extends SimpleFacetQuery implements DiscoveryQuery {

    private String defaultField;

    private String minimumShouldMatch;

    private String queryField;

    private String boostQuery;

    private String fields;

    public CustomSimpleFacetQuery() {
        super();
    }

    public String getDefaultField() {
        return defaultField;
    }

    public void setDefaultField(String defaultField) {
        this.defaultField = defaultField;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public void setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
    }

    public String getQueryField() {
        return queryField;
    }

    public void setQueryField(String queryField) {
        this.queryField = queryField;
    }

    public String getBoostQuery() {
        return boostQuery;
    }

    public void setBoostQuery(String boostQuery) {
        this.boostQuery = boostQuery;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

}