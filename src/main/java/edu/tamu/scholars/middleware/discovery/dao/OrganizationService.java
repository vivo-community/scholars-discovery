package edu.tamu.scholars.middleware.discovery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Organization;
import edu.tamu.scholars.middleware.discovery.model.repo.OrganizationRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class OrganizationService extends AbstractNestedDocumentService<Organization, edu.tamu.scholars.middleware.discovery.model.Organization, OrganizationRepo> {

    // TODO: figure out how to use findById returning Optional
    // TODO: figure out how to use name collections
    @Override
    @GraphQLQuery(name = "organization")
    public Organization getById(@GraphQLArgument(name = "id") String id) {
        return super.getById(id);
    }

    @Override
    @GraphQLQuery(name = "organizationCount")
    public long count() {
        return super.count();
    }

    @Override
    @GraphQLQuery(name = "organizationCount")
    public long count(String query, String[] fields, Map<String, List<String>> params) {
        return super.count(query, fields, params);
    }

    @Override
    @GraphQLQuery(name = "organizationExists")
    public boolean existsById(String id) {
        return super.existsById(id);
    }

    @Override
    @GraphQLQuery(name = "organizations")
    public Iterable<Organization> findAll() {
        return super.findAll();
    }

    @Override
    @GraphQLQuery(name = "organizations")
    public Iterable<Organization> findAll(@GraphQLArgument(name = "sort") Sort sort) {
        return super.findAll(sort);
    }

    @Override
    @GraphQLQuery(name = "organizations")
    public Page<Organization> findAll(@GraphQLArgument(name = "paging") Pageable pageable) {
        return super.findAll(pageable);
    }

    @Override
    @GraphQLQuery(name = "organizations")
    // @formatter:off
    public FacetPage<Organization> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "index") String index,
        @GraphQLArgument(name = "facets") String[] facets,
        @GraphQLArgument(name = "params") Map<String, List<String>> params,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        return super.search(query, index, facets, params, page);
    }

    @Override
    @GraphQLQuery(name = "organizations")
    public List<Organization> findByType(@GraphQLArgument(name = "type") String type) {
        return super.findByType(type);
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Organization.class;
    }

}
