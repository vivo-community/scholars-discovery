package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import edu.tamu.scholars.middleware.graphql.model.Collection;
import graphql.language.Field;

public class CollectionServiceTest extends AbstractNestedDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.Collection, Collection, CollectionService> {

    @Override
    protected Class<?> getType() {
        return edu.tamu.scholars.middleware.discovery.model.Collection.class;
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Collection.class;
    }

    @Override
    protected List<Field> getGraphQLEnvironmentFields() {
        return new ArrayList<Field>();
    }

}
