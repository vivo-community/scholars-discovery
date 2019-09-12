package edu.tamu.scholars.middleware.discovery.controller;

import edu.tamu.scholars.middleware.discovery.model.Organization;

public class OrganizationControllerTest extends AbstractSolrDocumentControllerTest<Organization> {

    @Override
    protected Class<?> getType() {
        return Organization.class;
    }

}
