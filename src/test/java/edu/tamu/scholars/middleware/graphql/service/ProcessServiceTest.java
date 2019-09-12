package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import edu.tamu.scholars.middleware.graphql.model.Process;
import graphql.language.Field;

public class ProcessServiceTest extends AbstractNestedDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.Process, Process, ProcessService> {

    @Override
    protected Class<?> getType() {
        return edu.tamu.scholars.middleware.discovery.model.Process.class;
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Process.class;
    }

    @Override
    protected List<Field> getGraphQLEnvironmentFields() {
        return new ArrayList<Field>();
    }

}
