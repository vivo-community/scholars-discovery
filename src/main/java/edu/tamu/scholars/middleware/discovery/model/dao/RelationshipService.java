package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Relationship;
import edu.tamu.scholars.middleware.discovery.model.repo.RelationshipRepo;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class RelationshipService extends AbstractNestedDocumentService<Relationship, edu.tamu.scholars.middleware.discovery.model.Relationship, RelationshipRepo> {

    @Override
    @GraphQLQuery(name = "relationships")
    public Iterable<Relationship> findAll() {
        return super.findAll();
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Relationship.class;
    }

}
