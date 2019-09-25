package edu.tamu.scholars.middleware.discovery.service.solr;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.mapping.SolrDocument;

import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.service.Indexer;

public class SolrIndexer implements Indexer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SolrTemplate solrTemplate;

    private final Class<AbstractIndexDocument> type;

    public SolrIndexer(Class<AbstractIndexDocument> type) {
        this.type = type;
    }

    public void index(Collection<AbstractIndexDocument> documents) {
        String collection = collection();
        try {
            solrTemplate.saveBeans(collection, documents);
            solrTemplate.commit(collection);
            logger.info(String.format("Saved %s batch of %s", name(), documents.size()));
        } catch (Exception e) {
            logger.warn("Failed to save batch. Attempting individually.");
            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
            documents.forEach(this::index);
        }
    }

    public void index(AbstractIndexDocument document) {
        String collection = collection();
        try {
            solrTemplate.saveBean(collection, document);
            solrTemplate.commit(collection);
            logger.info(String.format("Saved %s with id %s", name(), document.getId()));
        } catch (Exception e) {
            logger.warn(String.format("Failed to save document with id %s", document.getId()));
            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
        }
    }

    public Class<AbstractIndexDocument> type() {
        return type;
    }

    private String name() {
        return type.getSimpleName();
    }

    private String collection() {
        return type.getAnnotation(SolrDocument.class).collection();
    }

}