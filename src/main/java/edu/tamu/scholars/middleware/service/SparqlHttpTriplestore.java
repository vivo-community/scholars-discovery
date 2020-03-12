package edu.tamu.scholars.middleware.service;

import edu.tamu.scholars.middleware.config.model.TriplestoreConfig;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

public class SparqlHttpTriplestore implements Triplestore {

    private final static Logger logger = LoggerFactory.getLogger(SparqlHttpTriplestore.class);

    private final TriplestoreConfig config;

    public SparqlHttpTriplestore(TriplestoreConfig config) {
        this.config = config;
    }

    @Override
    public QueryExecution createQueryExecution(String query) {
        return QueryExecutionFactory.sparqlService(config.getDatasourceUrl(), query);
    }

    @Override
    public void init() {
        Instant start = Instant.now();
        logger.info("Initializing {}", config.getType().getSimpleName());
        logger.info("{} ready. {} seconds", config.getType().getSimpleName(), Duration.between(start, Instant.now()).toMillis() / 1000.0);
    }

    @Override
    public void destroy() {
        // no-op
    }

}
