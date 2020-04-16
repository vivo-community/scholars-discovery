package edu.tamu.scholars.middleware.discovery.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.solr.core.query.Field;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightEntry;

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.assembler.FacetAndHighlightPagedResourcesAssembler.Highlight;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import io.leangen.graphql.annotations.types.GraphQLType;

@GraphQLType(name = "FacetAndHighlightPage")
public class DiscoveryFacetAndHighlightPage<T> extends DiscoveryFacetPage<T> {

    private final List<Highlight> highlights;

    public DiscoveryFacetAndHighlightPage(List<T> content, PageInfo page, List<Facet> facets, List<Highlight> highlights) {
        super(content, page, facets);
        this.highlights = highlights;
    }

    public static <T> DiscoveryFacetAndHighlightPage<T> from(FacetAndHighlightPage<T> facetAndHighlightPage, List<FacetArg> facetArguments, HighlightArg highlightArg) {
        List<Facet> facets = buildFacets((FacetPage<T>) facetAndHighlightPage, facetArguments);
        List<Highlight> highlights = buildHighlights(facetAndHighlightPage, highlightArg);
        return new DiscoveryFacetAndHighlightPage<T>(facetAndHighlightPage.getContent(), PageInfo.from(facetAndHighlightPage), facets, highlights);
    }

    public static <T> List<Highlight> buildHighlights(FacetAndHighlightPage<T> facetAndHighlightPage, HighlightArg highlightArg) {
        List<Highlight> highlights = new ArrayList<>();
        facetAndHighlightPage.getHighlighted().stream().filter(DiscoveryFacetAndHighlightPage::hasHighlights).forEach(entry -> {
            String id = ((AbstractIndexDocument) entry.getEntity()).getId();
            Map<String, List<String>> snippets = new HashMap<>();
            entry.getHighlights().stream().filter(DiscoveryFacetAndHighlightPage::hasSnippets).forEach(highlight -> {
                Field field = highlight.getField();
                snippets.put(field.getName(), highlight.getSnipplets());
            });
            highlights.add(new Highlight(id, snippets));
        });
        return highlights;
    }

    public static <T> boolean hasHighlights(HighlightEntry<T> entry) {
        return CollectionUtils.isNotEmpty(entry.getHighlights());
    }

    public static <T> boolean hasSnippets(HighlightEntry.Highlight highlight) {
        return CollectionUtils.isNotEmpty(highlight.getSnipplets());
    }

    public List<Highlight> getHighlights() {
        return highlights;
    }

}