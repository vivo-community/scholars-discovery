package edu.tamu.scholars.middleware.discovery.service;

import java.util.stream.Stream;

import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;

public interface Harvester {

    public Stream<AbstractIndexDocument> harvest();

    public AbstractIndexDocument harvest(String subject);

    public Class<AbstractIndexDocument> type();

}
