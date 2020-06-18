package edu.tamu.scholars.middleware.service;

import java.time.Duration;
import java.time.Instant;
import java.io.File;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.tdb.TDB;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.tamu.scholars.middleware.config.model.TriplestoreConfig;

public class TTLTriplestore implements Triplestore {

    private final static Logger logger = LoggerFactory.getLogger(TDBTriplestore.class);

    private final TriplestoreConfig config;

    private Dataset dataset;

    public TTLTriplestore(TriplestoreConfig config) {
        this.config = config;
    }

    @Override
    public QueryExecution createQueryExecution(String query) {
        return QueryExecutionFactory.create(query, dataset);
    }

    @Override
    public void init() {
        Instant start = Instant.now();
        logger.info(String.format("Intializing %s", config.getType().getSimpleName()));
        // TODO: handle missing configurations
        TDB.getContext().setTrue(TDB.symUnionDefaultGraph);

        File f = new File(config.getDirectory());

        // Populates the array with list of files
        File[] files = f.listFiles();
        // read them into data set
        for (File file : files) {
            dataset = RDFDataMgr.loadDataset(file.getAbsolutePath(), RDFLanguages.TTL);
        }
        logger.info(String.format("%s ready. %s seconds", config.getType().getSimpleName(), Duration.between(start, Instant.now()).toMillis() / 1000.0));
    }

    @Override
    public void destroy() {
        dataset.close();
    }

}
