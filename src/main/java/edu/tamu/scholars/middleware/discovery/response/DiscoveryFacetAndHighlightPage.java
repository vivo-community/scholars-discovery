package edu.tamu.scholars.middleware.discovery.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.solr.core.query.result.FacetPage;

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import io.leangen.graphql.annotations.types.GraphQLType;

@GraphQLType(name = "FacetAndHighlightPage")
public class DiscoveryFacetAndHighlightPage<T> extends DiscoveryFacetPage<T> {

    private final Map<String, String> highlights;

    public DiscoveryFacetAndHighlightPage(List<T> content, PageInfo page, List<Facet> facets, Map<String, String> highlights) {
        super(content, page, facets);
        this.highlights = highlights;
    }

    public static <T> DiscoveryFacetAndHighlightPage<T> from(FacetAndHighlightPage<T> facetAndHighlightPage, List<FacetArg> facetArguments, HighlightArg highlightArg) {
        List<Facet> facets = buildFacets((FacetPage<T>) facetAndHighlightPage, facetArguments);
        Map<String, String> highlights = buildHighlights(facetAndHighlightPage, highlightArg);
        return new DiscoveryFacetAndHighlightPage<T>(facetAndHighlightPage.getContent(), PageInfo.from(facetAndHighlightPage), facets, highlights);
    }

    public static <T> Map<String, String> buildHighlights(FacetAndHighlightPage<T> facetAndHighlightPage, HighlightArg highlightArg) {
        System.out.println("\n\n" + highlightArg.getFields() + "\n\n");
        facetAndHighlightPage.getHighlighted().forEach(highlightEntry -> {
            highlightEntry.getHighlights().forEach(highlight -> {
                System.out.println("\t" + highlight);
                System.out.println("\t" + highlight.getField().getName());
                highlight.getSnipplets().forEach(snippet -> System.out.println("\t\t" + snippet));
            });
        });
        return new HashMap<>();
    }

    public Map<String, String> getHighlights() {
        return highlights;
    }

}