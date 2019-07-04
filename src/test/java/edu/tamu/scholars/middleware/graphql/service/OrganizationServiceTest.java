package edu.tamu.scholars.middleware.graphql.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.repo.OrganizationRepo;
import edu.tamu.scholars.middleware.graphql.model.Organization;

public class OrganizationServiceTest extends AbstractNestedDocumentServiceTest<Organization, edu.tamu.scholars.middleware.discovery.model.Organization, OrganizationRepo, OrganizationService> {

    @Value("classpath:mock/discovery/organization")
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

}
