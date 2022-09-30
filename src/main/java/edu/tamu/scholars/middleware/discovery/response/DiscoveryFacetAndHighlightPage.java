package edu.tamu.scholars.middleware.discovery.response;

import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.findPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.solr.core.query.Field;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightEntry;

import edu.tamu.scholars.middleware.discovery.DiscoveryConstants;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;

public class DiscoveryFacetAndHighlightPage<T> extends DiscoveryFacetPage<T> {

    private final static Pattern REFERENCE_PATTERN = Pattern.compile("^(.*?)::([\\w\\-\\:]*)(.*)$");

    private final List<Highlight> highlights;

    public DiscoveryFacetAndHighlightPage(List<T> content, PageInfo page, List<Facet> facets, List<Highlight> highlights) {
        super(content, page, facets);
        this.highlights = highlights;
    }

    @SuppressWarnings("unchecked")
    public static <T> DiscoveryFacetAndHighlightPage<T> from(FacetAndHighlightPage<T> facetAndHighlightPage, List<FacetArg> facetArguments, HighlightArg highlightArg) {
        List<Facet> facets = buildFacets((FacetPage<T>) facetAndHighlightPage, facetArguments);
        List<Highlight> highlights = buildHighlights(facetAndHighlightPage, highlightArg);
        return new DiscoveryFacetAndHighlightPage<T>(facetAndHighlightPage.getContent(), PageInfo.from(facetAndHighlightPage), facets, highlights);
    }

    public static <T> List<Highlight> buildHighlights(FacetAndHighlightPage<T> facetAndHighlightPage, HighlightArg highlightArg) {
        List<Highlight> highlights = new ArrayList<>();
        facetAndHighlightPage.getHighlighted().stream().filter(DiscoveryFacetAndHighlightPage::hasHighlights).forEach(entry -> {
            String id = ((AbstractIndexDocument) entry.getEntity()).getId();
            Map<String, List<Object>> snippets = new HashMap<>();
            entry.getHighlights().stream().filter(DiscoveryFacetAndHighlightPage::hasSnippets).forEach(highlight -> {
                Field field = highlight.getField();
                snippets.put(findPath(field.getName()), highlight.getSnipplets().stream().map(s -> {
                    Matcher matcher = REFERENCE_PATTERN.matcher(s);
                    if (matcher.find()) {
                        Map<String, String> value = new HashMap<>();
                        value.put(DiscoveryConstants.ID, matcher.group(2));
                        value.put(DiscoveryConstants.SNIPPET, matcher.group(1) + matcher.group(3));
                        return value;
                    }
                    return s;
                }).collect(Collectors.toList()));
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

    public static class Highlight {

        private final String id;

        private final Map<String, List<Object>> snippets;

        public Highlight(String id, Map<String, List<Object>> snippets) {
            this.id = id;
            this.snippets = snippets;
        }

        public String getId() {
            return id;
        }

        public Map<String, List<Object>> getSnippets() {
            return snippets;
        }

    }

}