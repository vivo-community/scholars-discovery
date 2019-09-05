package edu.tamu.scholars.middleware.export.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Organization;
import edu.tamu.scholars.middleware.discovery.model.repo.OrganizationRepo;

public class OrganizationExportControllerTest extends AbstractSolrDocumentExportControllerTest<Organization, OrganizationRepo> {

    @Value("classpath:mock/discovery/organizations")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Organization.class;
    }

    @Override
    protected String getPath() {
        return "/organizations";
    }

}
