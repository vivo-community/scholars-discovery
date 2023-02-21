package edu.tamu.scholars.middleware.discovery.model.repo;

import org.springframework.test.context.TestPropertySource;

import edu.tamu.scholars.middleware.discovery.model.Organization;

@TestPropertySource(properties = {
    "middleware.indexers[0].documentTypes[0]=edu.tamu.scholars.middleware.discovery.model.Organization"
})
public class OrganizationRepoTest extends AbstractSolrDocumentRepoTest<Organization> {

    @Override
    protected Class<?> getType() {
        return Organization.class;
    }

}
