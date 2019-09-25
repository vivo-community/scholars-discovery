package edu.tamu.scholars.middleware.config.model;

import java.util.ArrayList;
import java.util.List;

import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.service.Indexer;
import edu.tamu.scholars.middleware.discovery.service.solr.SolrIndexer;

public class IndexerConfig {

    private Class<? extends Indexer> type = SolrIndexer.class;

    private List<Class<? extends AbstractIndexDocument>> documentTypes = new ArrayList<Class<? extends AbstractIndexDocument>>();

    public IndexerConfig() {

    }

    public Class<? extends Indexer> getType() {
        return type;
    }

    public void setType(Class<? extends Indexer> type) {
        this.type = type;
    }

    public List<Class<? extends AbstractIndexDocument>> getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(List<Class<? extends AbstractIndexDocument>> documentTypes) {
        this.documentTypes = documentTypes;
    }

}
