
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
import edu.tamu.scholars.middleware.graphql.model.Person;
import edu.tamu.scholars.middleware.graphql.provider.DefaultHighlightProvider;
import edu.tamu.scholars.middleware.graphql.provider.DefaultPageRequestProvider;
import edu.tamu.scholars.middleware.graphql.provider.DefaultQueryProvider;
import graphql.language.Field;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLContext;


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
    public DiscoveryFacetAndHighlightPage<Person> search(
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

    // @Override
    @GraphQLMutation(name = "updatePersonPhone")
    public Person updatePersonPhone(
        @GraphQLContext Person person,
        @GraphQLArgument(name = "id") String id,
        @GraphQLArgument(name = "phone") String phone,
        @GraphQLEnvironment List<Field> fields) {
        Person p = this.updateFieldValue(id, fields, "phone", phone);
        return p;
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
