package edu.tamu.scholars.middleware.graphql.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage;
import edu.tamu.scholars.middleware.graphql.model.Person;
import edu.tamu.scholars.middleware.graphql.provider.DefaultPageRequestProvider;
import graphql.language.Field;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class PersonService extends AbstractNestedDocumentService<Person> {

    @Override
    @GraphQLQuery(name = "person")
    // @formatter:off
    public Person getById(
        @GraphQLArgument(name = "id") String id,
        @GraphQLEnvironment List<Field> fields
    ) {
    // @formatter:on
        return super.getById(id, fields);
    }

    @Override
    @GraphQLQuery(name = "people")
    // @formatter:off
    public DiscoveryFacetPage<Person> facetedSearch(
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
    public Class<Person> type() {
        return Person.class;
    }

    @Override
    protected Class<?> getOriginDocumentType() {
        return edu.tamu.scholars.middleware.discovery.model.Person.class;
    }

}
