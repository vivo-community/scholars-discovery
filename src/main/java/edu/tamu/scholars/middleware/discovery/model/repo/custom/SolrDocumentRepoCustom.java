package edu.tamu.scholars.middleware.discovery.model.repo.custom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.FacetPage;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.IndexArg;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;

public interface SolrDocumentRepoCustom<D extends AbstractSolrDocument> {

    public FacetPage<D> search(String query, Optional<IndexArg> index, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, Pageable page);

    public Cursor<D> stream(String query, Optional<IndexArg> index, List<FilterArg> filters, List<BoostArg> boosts, Sort sort);

    public List<D> findMostRecentlyUpdate(Integer limit);

    public long count(String query, List<FilterArg> filters);

    public Class<D> type();

}
