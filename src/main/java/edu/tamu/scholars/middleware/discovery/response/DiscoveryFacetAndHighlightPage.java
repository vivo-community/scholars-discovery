package edu.tamu.scholars.middleware.discovery.response;

import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.findPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.data.domain.Pageable;

import edu.tamu.scholars.middleware.discovery.DiscoveryConstants;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;

public class DiscoveryFacetAndHighlightPage<T> extends DiscoveryFacetPage<T> {

    private static final long serialVersionUID = 1932430579005159735L;

    private final static Pattern REFERENCE_PATTERN = Pattern.compile("^(.*?)::([\\w\\-\\:]*)(.*)$");

    private final List<Highlight> highlights;

    public DiscoveryFacetAndHighlightPage(List<T> content, Pageable pageable, long total, List<Facet> facets, List<Highlight> highlights) {
        super(content, pageable, total, facets);
        this.highlights = highlights;
    }

    public static <T> DiscoveryFacetAndHighlightPage<T> from(QueryResponse response, Pageable pageable, List<FacetArg> facetArguments, HighlightArg highlightArg, Class<T> type) {
        List<T> documents = response.getBeans(type);
        List<Facet> facets = buildFacets(response, facetArguments);
        List<Highlight> highlights = buildHighlights(response, highlightArg);
        SolrDocumentList results = response.getResults();

        return new DiscoveryFacetAndHighlightPage<T>(documents, pageable, results.getNumFound(), facets, highlights);
    }

    public static <T> List<Highlight> buildHighlights(QueryResponse response, HighlightArg highlightArg) {
        List<Highlight> highlights = new ArrayList<>();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        if (Objects.nonNull(highlighting)) {
            highlighting.entrySet().stream().filter(DiscoveryFacetAndHighlightPage::hasHighlights).forEach(hEntry -> {
                String id = hEntry.getKey();
                Map<String, List<Object>> snippets = new HashMap<>();
                hEntry.getValue().entrySet().stream().filter(DiscoveryFacetAndHighlightPage::hasSnippets).forEach(sEntry -> {
                    snippets.put(findPath(sEntry.getKey()), sEntry.getValue().stream().map(s -> {
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
        }

        return highlights;
    }

    public static <T> boolean hasHighlights(Entry<String, Map<String, List<String>>> entry ) {
        return !entry.getValue().isEmpty();
    }

    public static <T> boolean hasSnippets(Entry<String, List<String>> entry) {
        return !entry.getValue().isEmpty();
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
