package edu.tamu.scholars.middleware.service;

import java.time.Duration;
import java.time.Instant;

import org.apache.jena.query.Dataset;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.tamu.scholars.middleware.config.model.TriplestoreConfig;

public class TDBTriplestore implements Triplestore {

    private final static Logger logger = LoggerFactory.getLogger(TDBTriplestore.class);

    private final TriplestoreConfig config;

    private Dataset dataset;

    public TDBTriplestore(TriplestoreConfig config) {
        this.config = config;
    }

    @Override
    public void init() {
        Instant start = Instant.now();
        logger.info(String.format("Intializing %s", config.getType().getSimpleName()));
        // TODO: handle missing configurations
        TDB.getContext().setTrue(TDB.symUnionDefaultGraph);
        dataset = TDBFactory.createDataset(config.getDirectory());
        logger.info(String.format("%s ready. %s seconds", config.getType().getSimpleName(), Duration.between(start, Instant.now()).toMillis() / 1000.0));
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
