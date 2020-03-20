package edu.tamu.scholars.middleware.service;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.tdb.TDBFactory;

public class TestTriplestore implements Triplestore {

    private Dataset dataset;

    public QueryExecution createQueryExecution(String query) {
        return QueryExecutionFactory.create(query, dataset);
    }

    public void init() {
        dataset = TDBFactory.createDataset();
    }

    public void destroy() {
        dataset.close();
    }

}
