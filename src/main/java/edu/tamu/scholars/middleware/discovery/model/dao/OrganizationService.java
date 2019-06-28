package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Organization;
import edu.tamu.scholars.middleware.discovery.model.repo.OrganizationRepo;

@Service
public class OrganizationService extends AbstractNestedDocumentService<edu.tamu.scholars.middleware.discovery.model.generated.Organization, Organization, OrganizationRepo> {

    @Override
    protected Class<?> getNestedDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.generated.Organization.class;
    }

}
