package edu.tamu.scholars.middleware.discovery.model.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Concept;
import edu.tamu.scholars.middleware.discovery.model.repo.ConceptRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class ConceptService extends AbstractNestedDocumentService<Concept, edu.tamu.scholars.middleware.discovery.model.Concept, ConceptRepo> {

    // TODO: figure out how to use findById returning Optional
    // TODO: figure out how to use name collections
    @Override
    @GraphQLQuery(name = "concept")
    public Concept getById(@GraphQLArgument(name = "id") String id) {
        return super.getById(id);
    }

    @Override
    @GraphQLQuery(name = "concepts")
    public Iterable<Concept> findAll() {
        return super.findAll();
    }

    @Override
    @GraphQLQuery(name = "concepts")
    public Iterable<Concept> findAll(@GraphQLArgument(name = "sort") Sort sort) {
        return super.findAll(sort);
    }

    @Override
    @GraphQLQuery(name = "concepts")
    public Page<Concept> findAll(@GraphQLArgument(name = "paging") Pageable pageable) {
        return super.findAll(pageable);
    }

    @Override
    @GraphQLQuery(name = "concepts")
    // @formatter:off
    public FacetPage<Concept> search(
        @GraphQLArgument(name = "query") String query,
        @GraphQLArgument(name = "index") String index,
        @GraphQLArgument(name = "facets") String[] facets,
        @GraphQLArgument(name = "params") Map<String, List<String>> params,
        @GraphQLArgument(name = "paging") Pageable page
    ) {
    // @formatter:on
        params.keySet().stream().filter(key -> key.contains("_")).collect(Collectors.toList()).forEach(key -> {
            params.put(key.replace("_", "."), params.remove(key));
        });
        return super.search(query, index, facets, params, page);
    }

    @Override
    @GraphQLQuery(name = "concepts")
    public List<Concept> findByType(@GraphQLArgument(name = "type") String type) {
        return super.findByType(type);
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Concept.class;
    }

}
