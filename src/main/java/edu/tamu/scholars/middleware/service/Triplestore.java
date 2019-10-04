package edu.tamu.scholars.middleware.service;

import org.apache.jena.query.Dataset;

public interface Triplestore {

    public Dataset getDataset();

    public void init();

    public void destroy();

}
