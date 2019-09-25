package edu.tamu.scholars.middleware.service;

import org.apache.jena.query.Dataset;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;

import edu.tamu.scholars.middleware.config.model.TriplestoreConfig;

public class TDBTriplestore implements Triplestore {

    private final TriplestoreConfig config;

    private Dataset dataset;

    public TDBTriplestore(TriplestoreConfig config) {
        this.config = config;
    }

    @Override
    public void init() {
        // TODO: handle missing configurations
        TDB.getContext().setTrue(TDB.symUnionDefaultGraph);
        dataset = TDBFactory.createDataset(config.getDirectory());
    }

    @Override
    public void destroy() {
        dataset.close();
    }

    @Override
    public Dataset getDataset() {
        return dataset;
    }

}
