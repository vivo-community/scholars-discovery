package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.tamu.scholars.middleware.discovery.model.Organization;
import edu.tamu.scholars.middleware.discovery.model.doa.OrganizationService;
import edu.tamu.scholars.middleware.discovery.model.repo.OrganizationRepo;

public class OrganizationServiceTest extends AbstractSolrDocumentServiceTest<edu.tamu.scholars.middleware.discovery.model.generated.Organization, Organization, OrganizationRepo, OrganizationService> {

    @Value("classpath:mock/discovery/organization")
    private Resource mocksDirectory;

    @Override
    protected Resource getMocksDirectory() {
        return mocksDirectory;
    }

    @Override
    protected Class<?> getType() {
        return Organization.class;
    }

}
