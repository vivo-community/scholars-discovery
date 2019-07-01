package edu.tamu.scholars.middleware.discovery.model.repo.custom;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;

import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;

public interface SolrDocumentRepoCustom<D extends AbstractSolrDocument> {

    public FacetPage<D> search(String query, String index, String[] facets, Map<String, List<String>> params, Pageable page);

    public long count(String query, String[] fields, Map<String, List<String>> params);

}
