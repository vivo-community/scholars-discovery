package edu.tamu.scholars.middleware.discovery.response;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.tamu.scholars.middleware.discovery.utility.DateFormatUtility;
import io.leangen.graphql.annotations.types.GraphQLType;

import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.domain.Page;

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;

@GraphQLType(name = "FacetHighlightPage")
public class DiscoveryFacetHighlightPage<T> extends DiscoveryFacetPage<T> {
//public class DiscoveryFacetHighlightPage<T> extends DiscoveryPage<T> {
    private final List<Highlight> highlights;

    public DiscoveryFacetHighlightPage(List<T> content, PageInfo page,
       List<Facet> facets, List<Highlight> highlights) {
        super(content, page, facets);
        this.highlights = highlights;
    }

    /*
    public static <T> DiscoveryFacetHighlightPage<T> from(Page<T> page) {
        return new DiscoveryFacetHighlightPage<T>(page.getContent(), PageInfo.from(page));
    }

    public static <T> DiscoveryFacetHighlightPage<T> from(List<T> content, PageInfo page) {
        return new DiscoveryFacetHighlightPage<T>(content, page);
    }
    */

    public static <T> DiscoveryFacetHighlightPage<T> from(
        FacetAndHighlightPage<T> facetHighlightPage, 
        List<FacetArg> facetArguments, 
        List<HighlightArg> highlightArguments) {
        
            List<Facet> facets = buildFacets(facetHighlightPage, facetArguments);
            List<Highlight> highlights = buildHighlights(facetHighlightPage, highlightArguments);

        return new DiscoveryFacetHighlightPage<T>(facetHighlightPage.getContent(), 
            PageInfo.from(facetHighlightPage), facets, highlights);
    }

    public static <T> List<Facet> buildFacets(FacetAndHighlightPage<T> facetPage, List<FacetArg> facetArguments) {
        List<Facet> facets = new ArrayList<Facet>();

        facetPage.getFacetResultPages().forEach(facetFieldEntryPage -> {
            if (!facetFieldEntryPage.getContent().isEmpty()) {
                String field = facetFieldEntryPage.getContent().get(0).getField().getName();
                // NOTE: use getField instead of getCommand (any tagging is stripped off)
                Optional<FacetArg> facetArgument = facetArguments.stream().filter(fa -> fa.getField().equals(field)).findAny();
                if (facetArgument.isPresent()) {

                    // @formatter:off
                    List<FacetEntry> entries = facetFieldEntryPage.getContent().parallelStream()
                        .map(entry -> new FacetEntry(entry.getValue(), entry.getValueCount()))
                        .collect(Collectors.toMap(FacetEntry::getValueKey, fe -> fe, FacetEntry::merge)).values().parallelStream()
                        //.sorted(DiscoveryFacetPage.FacetEntryComparator.of(facetArgument.get().getSort()))
                        .collect(Collectors.toList());
                    // @formatter:on

                    int pageSize = facetArgument.get().getPageSize();
                    int pageNumber = facetArgument.get().getPageNumber();
                    int offset = pageSize * (pageNumber - 1);

                    long totalElements = entries.size();
                    int totalPages = (int) Math.ceil((double) entries.size() / (double) pageSize);

                    PageInfo pageInfo = PageInfo.from(pageSize, totalElements, totalPages, pageNumber);

                    int start = offset;
                    int end = offset + pageSize > entries.size() ? entries.size() : offset + pageSize;

                    facets.add(new Facet(field, DiscoveryPage.from(entries.subList(start, end), pageInfo)));
                }  
            } 
        });
        return facets;
    }

    public static <T> List<Highlight> buildHighlights(FacetAndHighlightPage<T> facetPage, List<HighlightArg> highlightArguments) {
        List<Highlight> highlights = new ArrayList<Highlight>();
        return highlights;
    }

    public List<Highlight> getHighlights() {
        return highlights;
    }

    @GraphQLType(name = "Highlight")
    public static class Highlight {

        private final String field;

        private final DiscoveryPage<HighlightEntry> entries;

        public Highlight(String field, DiscoveryPage<HighlightEntry> entries) {
            this.field = field;
            this.entries = entries;
        }

        public String getField() {
            return field;
        }

        public DiscoveryPage<HighlightEntry> getEntries() {
            return entries;
        }

    }

    @GraphQLType(name = "HighlightEntry")
    public static class HighlightEntry {

        private final String value;

        private final String text;

        public HighlightEntry(String value, String text) {
            this.value = value;
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public String getValueKey() {
            try {
                LocalDate ldv = DateFormatUtility.parse(value);
                return String.valueOf(ldv.getYear());
            } catch (DateTimeParseException dtpe) {
                return value;
            }
        }

        public String getText() {
            return text;
        }

    }

}