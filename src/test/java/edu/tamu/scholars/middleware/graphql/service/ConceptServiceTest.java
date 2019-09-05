package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.repo.ConceptRepo;
import edu.tamu.scholars.middleware.graphql.model.Concept;
import graphql.language.Field;

public class ConceptServiceTest extends AbstractNestedDocumentServiceTest<Concept, edu.tamu.scholars.middleware.discovery.model.Concept, ConceptRepo, ConceptService> {

    @Value("classpath:mock/discovery/concepts")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return edu.tamu.scholars.middleware.discovery.model.Concept.class;
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Concept.class;
    }

    @Override
    protected List<Field> getGraphQLEnvironmentFields() {
        return new ArrayList<Field>();
    }

}
