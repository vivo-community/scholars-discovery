package edu.tamu.scholars.middleware.discovery.model.repo;

import edu.tamu.scholars.middleware.discovery.model.Organization;

public class OrganizationRepoTest extends AbstractSolrDocumentRepoTest<Organization> {

    @Override
    protected Class<?> getType() {
        return Organization.class;
    }

}
