package edu.tamu.scholars.middleware.config.model;

import edu.tamu.scholars.middleware.discovery.component.Indexer;
import edu.tamu.scholars.middleware.discovery.component.solr.SolrIndexer;

public class IndexerConfig extends IndexDocumentTypesConfig {

    private Class<? extends Indexer> type = SolrIndexer.class;

    public IndexerConfig() {
        super();
    }

    public Class<? extends Indexer> getType() {
        return type;
    }

    public void setType(Class<? extends Indexer> type) {
        this.type = type;
    }

}
