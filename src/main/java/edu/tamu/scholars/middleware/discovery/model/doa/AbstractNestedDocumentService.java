package edu.tamu.scholars.middleware.discovery.model.doa;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;

@NoRepositoryBean
public abstract class AbstractNestedDocumentService<ND extends AbstractNestedDocument, D extends AbstractSolrDocument, R extends SolrDocumentRepo<D>> implements SolrDocumentRepo<ND> {

    @Autowired
    private R repo;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public <NS extends ND> NS save(NS nestedDocument, Duration commitWithin) {
        throw new UnsupportedOperationException(String.format("%s is read only", getNestedDocumentType()));
    }

    @Override
    public <NS extends ND> Iterable<NS> saveAll(Iterable<NS> nestedDocuments, Duration commitWithin) {
        throw new UnsupportedOperationException(String.format("%s is read only", getNestedDocumentType()));
    }

    @Override
    public long count() {
        return repo.count();
    }

    @Override
    public Iterable<ND> findAll(Sort sort) {
        return StreamSupport.stream(repo.findAll(sort).spliterator(), false).map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public Page<ND> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(this::toNested);
    }

    @Override
    public <NS extends ND> Iterable<NS> saveAll(Iterable<NS> nestedDocuments) {
        throw new UnsupportedOperationException(String.format("%s is read only", getNestedDocumentType()));
    }

    @Override
    public Optional<ND> findById(String id) {
        Optional<ND> nestedDocument = Optional.empty();
        Optional<D> document = repo.findById(id);
        if (document.isPresent()) {
            nestedDocument = Optional.of(toNested(document.get()));
        }
        return nestedDocument;
    }

    @Override
    public boolean existsById(String id) {
        return repo.existsById(id);
    }

    @Override
    public Iterable<ND> findAll() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public Iterable<ND> findAllById(Iterable<String> ids) {
        return StreamSupport.stream(repo.findAllById(ids).spliterator(), false).map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException(String.format("%s is read only", getNestedDocumentType()));
    }

    @Override
    public void deleteAll(Iterable<? extends ND> nestedDocuments) {
        throw new UnsupportedOperationException(String.format("%s is read only", getNestedDocumentType()));
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException(String.format("%s is read only", getNestedDocumentType()));
    }

    @Override
    public FacetPage<ND> search(String query, String index, String[] facets, MultiValueMap<String, String> params, Pageable page) {
        return (FacetPage<ND>) repo.search(query, index, facets, params, page).map(this::toNested);
    }

    @Override
    public long count(String query, String[] fields, MultiValueMap<String, String> params) {
        return repo.count(query, fields, params);
    }

    @Override
    public <NS extends ND> NS save(NS nestedDocument) {
        throw new UnsupportedOperationException(String.format("%s is read only", getNestedDocumentType()));
    }

    @Override
    public void delete(ND nestedDocument) {
        throw new UnsupportedOperationException(String.format("%s is read only", getNestedDocumentType()));
    }

    @Override
    public List<ND> findByType(String type) {
        return repo.findByType(type).stream().map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public List<ND> findByIdIn(List<String> ids) {
        return repo.findByIdIn(ids).stream().map(this::toNested).collect(Collectors.toList());
    }

    @Override
    public List<ND> findBySyncIdsIn(List<String> syncIds) {
        return repo.findBySyncIdsIn(syncIds).stream().map(this::toNested).collect(Collectors.toList());
    }

    protected abstract Class<?> getNestedDocumentType();

    @SuppressWarnings("unchecked")
    private ND toNested(D document) {
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(document);
            return (ND) mapper.readValue(json, getNestedDocumentType());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }

}
