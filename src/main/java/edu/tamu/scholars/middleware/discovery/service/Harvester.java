package edu.tamu.scholars.middleware.discovery.service;

import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import reactor.core.publisher.Flux;

public interface Harvester {

    public Flux<AbstractIndexDocument> harvest();

    public AbstractIndexDocument harvest(String subject);

    public Class<AbstractIndexDocument> type();

}
