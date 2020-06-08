package edu.tamu.scholars.middleware.graphql.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetAndHighlightPage;
import edu.tamu.scholars.middleware.graphql.model.Relationship;
import edu.tamu.scholars.middleware.graphql.provider.DefaultHighlightProvider;
import edu.tamu.scholars.middleware.graphql.provider.DefaultPageRequestProvider;
import edu.tamu.scholars.middleware.graphql.provider.DefaultQueryProvider;
import graphql.language.Field;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class RelationshipService extends AbstractNestedDocumentService<Relationship> {

    @Override
    @GraphQLQuery(name = "relationship")
    // @formatter:off
    public Relationship getById(
        @GraphQLArgument(name = "id") String id,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.getById(id, fields);
    }

    @Override
    @GraphQLQuery(name = "relationships")
    // @formatter:off
    public DiscoveryFacetAndHighlightPage<Relationship> search(
        @GraphQLArgument(name = "query", defaultValueProvider = DefaultQueryProvider.class) QueryArg query,
        @GraphQLArgument(name = "facets", defaultValue = "[]") List<FacetArg> facets,
        @GraphQLArgument(name = "filters", defaultValue = "[]") List<FilterArg> filters,
        @GraphQLArgument(name = "boosts", defaultValue = "[]") List<BoostArg> boosts,
        @GraphQLArgument(name = "highlight", defaultValueProvider = DefaultHighlightProvider.class) HighlightArg highlight,
        @GraphQLArgument(name = "paging", defaultValueProvider = DefaultPageRequestProvider.class) Pageable page,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.search(query, facets, filters, boosts, highlight, page, fields);
    }

    @Override
    public Class<Relationship> type() {
        return Relationship.class;
    }

    @Override
    protected Class<?> getOriginDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.Relationship.class;
    }

}
