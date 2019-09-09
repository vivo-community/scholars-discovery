package edu.tamu.scholars.middleware.discovery.response;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.FacetOptions.FacetSort;
import org.springframework.data.solr.core.query.result.FacetPage;

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetSortArg;
import edu.tamu.scholars.middleware.utility.DateFormatUtility;
import io.leangen.graphql.annotations.types.GraphQLType;

@GraphQLType(name = "FacetPage")
public class DiscoveryFacetPage<T> extends DiscoveryPage<T> {

    private final List<Facet> facets;

    public DiscoveryFacetPage(List<T> content, PageInfo page, List<Facet> facets) {
        super(content, page);
        this.facets = facets;
    }

    public static <T> DiscoveryFacetPage<T> from(FacetPage<T> facetPage, List<FacetArg> facetArguments) {
        List<Facet> facets = buildFacets(facetPage, facetArguments);
        return new DiscoveryFacetPage<T>(facetPage.getContent(), PageInfo.from(facetPage), facets);
    }

    public static <T> List<Facet> buildFacets(FacetPage<T> facetPage, List<FacetArg> facetArguments) {
        long startTime = System.nanoTime();
        List<Facet> facets = new ArrayList<Facet>();

        facetPage.getFacetResultPages().forEach(facetFieldEntryPage -> {

            if (!facetFieldEntryPage.getContent().isEmpty()) {

                String field = facetFieldEntryPage.getContent().get(0).getField().getName();

                Optional<FacetArg> facetArgument = facetArguments.stream().filter(fa -> fa.getProperty().equals(field)).findAny();

                if (facetArgument.isPresent()) {

                    // @formatter:off
                    List<FacetEntry> entries = facetFieldEntryPage.getContent().parallelStream()
                        .map(entry -> new FacetEntry(entry.getValue(), entry.getValueCount()))
                        .collect(Collectors.toMap(FacetEntry::getValueKey, fe -> fe, FacetEntry::merge)).values().parallelStream()
                        .sorted(FacetEntryComparator.of(facetArgument.get().getSort()))
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
        System.out.println("\nTook " + (double) (System.nanoTime() - startTime) / (double) 1000000 + " milliseconds to build facets\n");
        return facets;
    }

    private static class FacetEntryComparator implements Comparator<FacetEntry> {

        private final FacetSortArg facetSort;

        private FacetEntryComparator(FacetSortArg facetSort) {
            this.facetSort = facetSort;
        }

        @Override
        public int compare(FacetEntry e1, FacetEntry e2) {
            if (facetSort.getProperty().equals(FacetSort.COUNT)) {
                return facetSort.getDirection().equals(Direction.ASC) ? Long.compare(e1.count, e2.count) : Long.compare(e2.count, e1.count);
            }
            try {
                LocalDate ld1 = DateFormatUtility.parse(e1.value);
                LocalDate ld2 = DateFormatUtility.parse(e2.value);
                return facetSort.getDirection().equals(Direction.ASC) ? ld1.compareTo(ld2) : ld2.compareTo(ld1);
            } catch (DateTimeParseException dtpe) {
                try {
                    Double d1 = Double.parseDouble(e1.value);
                    Double d2 = Double.parseDouble(e2.value);
                    return facetSort.getDirection().equals(Direction.ASC) ? d1.compareTo(d2) : d2.compareTo(d1);
                } catch (NumberFormatException nfe) {
                    return facetSort.getDirection().equals(Direction.ASC) ? e1.value.compareTo(e2.value) : e2.value.compareTo(e1.value);
                }
            }
        }

        private static FacetEntryComparator of(FacetSortArg facetSort) {
            return new FacetEntryComparator(facetSort);
        }

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

        public String getValueKey() {
            try {
                LocalDate ldv = DateFormatUtility.parse(value);
                return String.valueOf(ldv.getYear());
            } catch (DateTimeParseException dtpe) {
                return value;
            }
        }

        public long getCount() {
            return count;
        }

        public static FacetEntry merge(FacetEntry src, FacetEntry dest) {
            return new FacetEntry(src.value, src.count + dest.count);
        }

    }

}