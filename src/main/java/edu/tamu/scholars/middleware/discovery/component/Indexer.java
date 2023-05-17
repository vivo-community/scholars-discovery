package edu.tamu.scholars.middleware.discovery.component;

import java.util.Collection;

import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;

public interface Indexer {

    public void init();

    public void index(Collection<AbstractIndexDocument> documents);

    public void index(AbstractIndexDocument document);

    public void optimize();

    public Class<AbstractIndexDocument> type();

}