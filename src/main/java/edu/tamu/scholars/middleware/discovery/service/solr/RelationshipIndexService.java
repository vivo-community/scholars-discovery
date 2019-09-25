package edu.tamu.scholars.middleware.discovery.service;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Relationship;

@Service
public class RelationshipIndexService extends AbstractSolrIndexService<Relationship> {

    @Override
    public Class<?> type() {
        return Relationship.class;
    }

}
