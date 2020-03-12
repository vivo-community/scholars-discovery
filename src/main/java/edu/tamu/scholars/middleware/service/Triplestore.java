package edu.tamu.scholars.middleware.service;

import org.apache.jena.query.QueryExecution;

public interface Triplestore {

    public QueryExecution createQueryExecution(String query);

    public void init();

    public void destroy();

}
