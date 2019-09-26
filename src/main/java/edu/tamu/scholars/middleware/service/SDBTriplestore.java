package edu.tamu.scholars.middleware.service;

import java.time.Duration;
import java.time.Instant;

import org.apache.jena.query.Dataset;
import org.apache.jena.sdb.SDB;
import org.apache.jena.sdb.SDBFactory;
import org.apache.jena.sdb.Store;
import org.apache.jena.sdb.StoreDesc;
import org.apache.jena.sdb.sql.SDBConnection;
import org.apache.jena.sdb.store.DatabaseType;
import org.apache.jena.sdb.store.LayoutType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.tamu.scholars.middleware.config.model.TriplestoreConfig;

public class SDBTriplestore implements Triplestore {

    private final static Logger logger = LoggerFactory.getLogger(SDBTriplestore.class);

    private final TriplestoreConfig config;

    private Store store;

    private Dataset dataset;

    public SDBTriplestore(TriplestoreConfig config) {
        this.config = config;
    }

    @Override
    public void init() {
        Instant start = Instant.now();
        logger.info(String.format("Intializing %s", config.getType().getSimpleName()));
        // TODO: handle missing configurations
        SDB.getContext().setTrue(SDB.unionDefaultGraph);
        SDB.getContext().set(SDB.jdbcStream, config.isJdbcStream());
        SDB.getContext().set(SDB.jdbcFetchSize, config.getJdbcFetchSize());
        SDB.getContext().set(SDB.streamGraphAPI, config.isStreamGraphAPI());
        SDB.getContext().set(SDB.annotateGeneratedSQL, config.isAnnotateGeneratedSQL());
        StoreDesc storeDesc = new StoreDesc(LayoutType.fetch(config.getLayoutType()), DatabaseType.fetch(config.getDatabaseType()));
        SDBConnection conn = new SDBConnection(config.getDatasourceUrl(), config.getUsername(), config.getPassword());
        store = SDBFactory.connectStore(conn, storeDesc);
        dataset = SDBFactory.connectDataset(store);
        logger.info(String.format("%s ready. %s seconds", config.getType().getSimpleName(), Duration.between(start, Instant.now()).toMillis() / 1000.0));
    }

    @Override
    public void destroy() {
        store.getConnection().close();
        store.close();
        dataset.close();
    }

    @Override
    public Dataset getDataset() {
        return dataset;
    }

}
