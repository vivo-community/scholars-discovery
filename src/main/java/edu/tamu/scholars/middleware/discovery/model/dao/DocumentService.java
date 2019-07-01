package edu.tamu.scholars.middleware.discovery.model.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.generated.Document;
import edu.tamu.scholars.middleware.discovery.model.repo.DocumentRepo;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;

@Service
public class DocumentService extends AbstractNestedDocumentService<Document, edu.tamu.scholars.middleware.discovery.model.Document, DocumentRepo> {

//    @Override
//    @GraphQLQuery(name = "document")
//    public @GraphQLNonNull Optional<Document> findById(@GraphQLArgument(name = "id") String id) {
//        return super.findById(id);
//    }

    @Override
    @GraphQLQuery(name = "documents")
    public Iterable<Document> findAll() {
        return super.findAll();
    }

    @Override
    @GraphQLQuery(name = "documents")
    public Iterable<Document> findAll(@GraphQLArgument(name = "sort") Sort sort) {
        return super.findAll(sort);
    }

    @Override
    @GraphQLQuery(name = "documents")
    public Page<Document> findAll(@GraphQLArgument(name = "paging") Pageable pageable) {
        return super.findAll(pageable);
    }

    @Override
    @GraphQLQuery(name = "documents")
    // @formatter:off
    public FacetPage<Document> search(
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
    @GraphQLQuery(name = "documents")
    public List<Document> findByType(@GraphQLArgument(name = "type") String type) {
        return super.findByType(type);
    }

    @Override
    protected Class<?> getNestedDocumentType() {
        return Document.class;
    }

}
