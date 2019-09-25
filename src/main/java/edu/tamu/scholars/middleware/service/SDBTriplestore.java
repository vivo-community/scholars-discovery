package edu.tamu.scholars.middleware.service;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sdb.SDB;
import org.apache.jena.sdb.SDBFactory;
import org.apache.jena.sdb.Store;
import org.apache.jena.sdb.StoreDesc;
import org.apache.jena.sdb.sql.SDBConnection;
import org.apache.jena.sdb.store.DatabaseType;
import org.apache.jena.sdb.store.LayoutType;

import edu.tamu.scholars.middleware.config.model.TriplestoreConfig;

public class SDBTriplestore implements Triplestore {

    private final TriplestoreConfig config;

    private Store store;

    private Dataset dataset;

    public SDBTriplestore(TriplestoreConfig config) {
        this.config = config;
    }

    @Override
    public void init() {
        // TODO: handle missing configurations
        SDB.getContext().setTrue(SDB.unionDefaultGraph);
        SDB.getContext().set(SDB.jdbcStream, config.isJdbcStream());
        SDB.getContext().set(SDB.jdbcFetchSize, config.getJdbcFetchSize());
        SDB.getContext().set(SDB.streamGraphAPI, config.isStreamGraphAPI());
        SDB.getContext().set(SDB.annotateGeneratedSQL, config.isAnnotateGeneratedSQL());
        StoreDesc storeDesc = new StoreDesc(LayoutType.fetch(config.getLayoutType()), DatabaseType.fetch(config.getDatabaseType()));
        SDBConnection conn = new SDBConnection(config.getDatasourceUrl(), config.getUsername(), config.getPassword());
        store = SDBFactory.connectStore(conn, storeDesc);
        // dataset = SDBFactory.connectDataset(store);
        dataset = DatasetFactory.create();
        Model model = SDBFactory.connectDefaultModel(store);
        dataset.setDefaultModel(model);
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
