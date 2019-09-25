package edu.tamu.scholars.middleware.discovery.service;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Organization;

@Service
public class OrganizationIndexService extends AbstractSolrIndexService<Organization> {

    @Override
    public Class<?> type() {
        return Organization.class;
    }

}
