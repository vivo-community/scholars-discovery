package edu.tamu.scholars.middleware.discovery.model.repo.custom;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.DiscoveryNetworkDescriptor;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetAndHighlightPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryNetwork;

public interface IndexDocumentRepoCustom<D extends AbstractIndexDocument> {

    public List<D> findByType(String type);

    public List<D> findByIdIn(List<String> ids);

    public long count(String query, List<FilterArg> filters);

    public List<D> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters);

    public DiscoveryFacetAndHighlightPage<D> search(QueryArg query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, HighlightArg highlight, Pageable page);

    public CompletableFuture<Iterator<D>> export(QueryArg query, List<FilterArg> filters, List<BoostArg> boosts, Sort sort);

    public DiscoveryNetwork network(DiscoveryNetworkDescriptor dataNetworkDescriptor);

}
