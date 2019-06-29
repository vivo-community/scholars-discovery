package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Concept;
import edu.tamu.scholars.middleware.discovery.model.repo.ConceptRepo;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class ConceptService extends AbstractNestedDocumentService<Concept, edu.tamu.scholars.middleware.discovery.model.Concept, ConceptRepo> {

    @Override
    @GraphQLQuery(name = "concepts")
    public Iterable<Concept> findAll() {
        return super.findAll();
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Concept.class;
    }

}
