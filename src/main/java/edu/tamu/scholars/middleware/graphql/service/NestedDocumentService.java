package edu.tamu.scholars.middleware.graphql.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetHighlightPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryPage;
import edu.tamu.scholars.middleware.graphql.model.AbstractNestedDocument;
import graphql.language.Field;

public interface NestedDocumentService<ND extends AbstractNestedDocument> {

    public long count();

    public long count(String query, List<FilterArg> filters);

    public boolean existsById(String id);

    public ND getById(String id, List<Field> fields);

    public List<ND> findByIdIn(List<String> ids, List<Field> fields);

    public List<ND> findByType(String type, List<Field> fields);

    public List<ND> findByType(String type, List<FilterArg> filters, List<Field> fields);

    public List<ND> findMostRecentlyUpdate(Integer limit, List<Field> fields);

    public List<ND> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters, List<Field> fields);

    public Iterable<ND> findAll(List<Field> fields);

    public Iterable<ND> findAll(List<FilterArg> filters, List<Field> fields);

    public Iterable<ND> findAll(Sort sort, List<Field> fields);

    public Iterable<ND> findAll(List<FilterArg> filters, Sort sort, List<Field> fields);

    public DiscoveryPage<ND> findAll(Pageable page, List<Field> fields);

    public DiscoveryPage<ND> findAll(List<FilterArg> filters, Pageable page, List<Field> fields);

    public DiscoveryFacetPage<ND> search(String query, Pageable page, List<Field> fields);

    public DiscoveryFacetPage<ND> search(String query, List<BoostArg> boosts, Pageable page, List<Field> fields);

    public DiscoveryFacetPage<ND> filterSearch(String query, List<FilterArg> filters, Pageable page, List<Field> fields);

    public DiscoveryFacetPage<ND> filterSearch(String query, List<FilterArg> filters, List<BoostArg> boosts, Pageable page, List<Field> fields);

    public DiscoveryFacetPage<ND> facetedSearch(String query, List<FacetArg> facets, Pageable page, List<Field> fields);

    public DiscoveryFacetPage<ND> facetedSearch(String query, List<FacetArg> facets, List<FilterArg> filters, Pageable page, List<Field> fields);

    public DiscoveryFacetPage<ND> facetedSearch(String query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, Pageable page, List<Field> fields);

    public List<ND> findBySyncIdsIn(List<String> syncIds);

    public Class<ND> type();

}
