package edu.tamu.scholars.middleware.graphql.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.repo.OrganizationRepo;
import edu.tamu.scholars.middleware.graphql.model.Organization;
import graphql.language.Field;

public class OrganizationServiceTest extends AbstractNestedDocumentServiceTest<Organization, edu.tamu.scholars.middleware.discovery.model.Organization, OrganizationRepo, OrganizationService> {

    @Value("classpath:mock/discovery/organizations")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return edu.tamu.scholars.middleware.discovery.model.Organization.class;
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Organization.class;
    }

    @Override
    protected List<Field> getGraphQLEnvironmentFields() {
        return new ArrayList<Field>();
    }

}
