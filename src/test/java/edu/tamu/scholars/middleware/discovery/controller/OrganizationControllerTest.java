package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Organization;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Organization"
})
public class OrganizationControllerTest extends AbstractSolrDocumentControllerTest<Organization> {

    @Override
    protected Class<?> getType() {
        return Organization.class;
    }

}
