package edu.tamu.scholars.middleware.discovery.model.repo.custom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.FacetPage;

import edu.tamu.scholars.middleware.discovery.argument.Facet;
import edu.tamu.scholars.middleware.discovery.argument.Filter;
import edu.tamu.scholars.middleware.discovery.argument.Index;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;

public interface SolrDocumentRepoCustom<D extends AbstractSolrDocument> {

    public FacetPage<D> search(String query, Optional<Index> index, List<Facet> facets, List<Filter> filters, Pageable page);

    public Cursor<D> stream(String query, Optional<Index> index, List<Filter> filters, Sort sort);

    public List<D> findMostRecentlyUpdate(Integer limit);

    public long count(String query, List<Filter> filters);

    public Class<D> type();

}
