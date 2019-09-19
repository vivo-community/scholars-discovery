package edu.tamu.scholars.middleware.export.controller;

import edu.tamu.scholars.middleware.discovery.model.Organization;

public class OrganizationExportControllerTest extends AbstractSolrDocumentExportControllerTest<Organization> {

    @Override
    protected Class<?> getType() {
        return Organization.class;
    }

}
