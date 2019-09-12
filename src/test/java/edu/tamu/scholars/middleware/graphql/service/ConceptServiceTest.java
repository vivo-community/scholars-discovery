package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import edu.tamu.scholars.middleware.graphql.model.Concept;
import graphql.language.Field;

public class ConceptServiceTest extends AbstractNestedDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.Concept, Concept, ConceptService> {

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
