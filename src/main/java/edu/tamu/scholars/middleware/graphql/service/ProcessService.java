package edu.tamu.scholars.middleware.graphql.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage;
import edu.tamu.scholars.middleware.graphql.model.Process;
import edu.tamu.scholars.middleware.graphql.provider.DefaultPageRequestProvider;
import graphql.language.Field;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class ProcessService extends AbstractNestedDocumentService<Process> {

    @Override
    @GraphQLQuery(name = "process")
    // @formatter:off
    public Process getById(
        @GraphQLArgument(name = "id") String id,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.getById(id, fields);
    }

    @Override
    @GraphQLQuery(name = "processes")
    // @formatter:off
    public DiscoveryFacetPage<Process> facetedSearch(
        @GraphQLArgument(name = "query", defaultValue = "*") String query,
        @GraphQLArgument(name = "facets", defaultValue = "[]") List<FacetArg> facets,
        @GraphQLArgument(name = "filters", defaultValue = "[]") List<FilterArg> filters,
        @GraphQLArgument(name = "boosts", defaultValue = "[]") List<BoostArg> boosts,
        @GraphQLArgument(name = "paging", defaultValueProvider = DefaultPageRequestProvider.class) Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.facetedSearch(query, facets, filters, boosts, page, fields);
    }

    @Override
    public Class<Process> type() {
        return Process.class;
    }

    @Override
    protected Class<?> getOriginDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.Process.class;
    }

}
