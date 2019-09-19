package edu.tamu.scholars.middleware.discovery.service;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Collection;

@Service
public class CollectionIndexService extends AbstractSolrIndexService<Collection> {

    @Override
    public Class<?> type() {
        return Collection.class;
    }

}
