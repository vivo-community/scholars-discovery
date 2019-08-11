package edu.tamu.scholars.middleware.discovery.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import io.leangen.graphql.annotations.types.GraphQLType;

@GraphQLType(name = "FacetPage")
public class DiscoveryFacetPage<T> extends DiscoveryPage<T> {

    private final List<Facet> facets;

    public DiscoveryFacetPage(List<T> content, PageInfo page, List<Facet> facets) {
        super(content, page);
        this.facets = facets;
    }

    public static <T> DiscoveryFacetPage<T> from(FacetPage<T> facetPage, List<FacetArg> facetArguments, Class<?> type) {
        List<Facet> facets = buildFacets(facetPage, facetArguments, type);
        return new DiscoveryFacetPage<T>(facetPage.getContent(), PageInfo.from(facetPage), facets);
    }

    public static <T> List<Facet> buildFacets(FacetPage<T> facetPage, List<FacetArg> facetArguments, Class<?> type) {
        List<Facet> facets = new ArrayList<Facet>();

        facetPage.getFacetResultPages().forEach(facetFieldEntryPage -> {
            Optional<String> field = Optional.empty();

            List<FacetEntry> entries = new ArrayList<FacetEntry>();

            Optional<FacetArg> facetArgument = Optional.empty();

            for (FacetFieldEntry facetFieldEntry : facetFieldEntryPage.getContent()) {
                if (!field.isPresent()) {
                    field = Optional.of(facetFieldEntry.getField().getName());
                    facetArgument = facetArguments.stream().filter(fa -> fa.getPath(type).equals(facetFieldEntry.getField().getName())).findAny();
                }
                entries.add(new FacetEntry(facetFieldEntry.getValue(), facetFieldEntry.getValueCount()));
            }

            if (field.isPresent()) {
                int pageSize = facetArgument.get().getPageSize();
                int pageNumber = facetArgument.get().getPageNumber();
                int offset = pageSize * pageNumber;

                long totalElements = facetFieldEntryPage.getTotalElements();
                int totalPages = facetFieldEntryPage.getTotalPages();

                PageInfo pageInfo = PageInfo.from(pageSize, totalElements, totalPages, pageNumber);

                int start = offset;
                int end = offset + pageSize > entries.size() ? entries.size() : offset + pageSize;

                facets.add(new Facet(field.get(), DiscoveryPage.from(entries.subList(start, end), pageInfo)));
            }

        });
        return facets;
    }

    public List<Facet> getFacets() {
        return facets;
    }

    @GraphQLType(name = "Facet")
    public static class Facet {

        private final String field;

        private final DiscoveryPage<FacetEntry> entries;

        public Facet(String field, DiscoveryPage<FacetEntry> entries) {
            this.field = field;
            this.entries = entries;
        }

        public String getField() {
            return field;
        }

        public DiscoveryPage<FacetEntry> getEntries() {
            return entries;
        }

    }

    @GraphQLType(name = "FacetEntry")
    public static class FacetEntry {

        private final String value;

        private final long count;

        public FacetEntry(String value, long count) {
            this.value = value;
            this.count = count;
        }

        public String getValue() {
            return value;
        }

        public long getCount() {
            return count;
        }

    }

}