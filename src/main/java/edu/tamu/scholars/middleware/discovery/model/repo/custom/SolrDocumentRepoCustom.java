package edu.tamu.scholars.middleware.discovery.model.repo.custom;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;

public interface SolrDocumentRepoCustom<D extends AbstractIndexDocument> {

    public long count(String query, List<FilterArg> filters);

    public List<D> findAll(List<FilterArg> filters);

    public List<D> findAll(List<FilterArg> filters, Sort sort);

    public Page<D> findAll(List<FilterArg> filters, Pageable page);

    public List<D> findByType(String type, List<FilterArg> filters);

    public List<D> findMostRecentlyUpdate(Integer limit);

    public List<D> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters);

    public FacetAndHighlightPage<D> search(QueryArg query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, HighlightArg highlight, Pageable page);

    public Cursor<D> stream(QueryArg query, List<FilterArg> filters, List<BoostArg> boosts, Sort sort);

    public Class<D> type();

}
