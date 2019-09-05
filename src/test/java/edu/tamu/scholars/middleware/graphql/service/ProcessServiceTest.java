package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.repo.ProcessRepo;
import edu.tamu.scholars.middleware.graphql.model.Process;
import graphql.language.Field;

public class ProcessServiceTest extends AbstractNestedDocumentServiceTest<Process, edu.tamu.scholars.middleware.discovery.model.Process, ProcessRepo, ProcessService> {

    @Value("classpath:mock/discovery/processes")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

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
