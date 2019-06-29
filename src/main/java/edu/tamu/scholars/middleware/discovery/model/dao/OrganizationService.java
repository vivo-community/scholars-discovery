package edu.tamu.scholars.middleware.discovery.model.dao;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Organization;
import edu.tamu.scholars.middleware.discovery.model.repo.OrganizationRepo;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class OrganizationService extends AbstractNestedDocumentService<Organization, edu.tamu.scholars.middleware.discovery.model.Organization, OrganizationRepo> {

    @Override
    @GraphQLQuery(name = "organizations")
    public Iterable<Organization> findAll() {
        return super.findAll();
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Organization.class;
    }

}
