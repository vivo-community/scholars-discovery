package edu.tamu.scholars.middleware.discovery.query;

import org.springframework.data.solr.core.query.Query;

public interface DiscoveryQuery extends Query {

    public String getDefaultField();

    public void setDefaultField(String defaultField);

    public String getMinimumShouldMatch();

    public void setMinimumShouldMatch(String minimumShouldMatch);

    public String getQueryField();

    public void setQueryField(String queryField);

    public String getBoostQuery();

    public void setBoostQuery(String boostQuery);

    public String getFields();

    public void setFields(String fields);

}