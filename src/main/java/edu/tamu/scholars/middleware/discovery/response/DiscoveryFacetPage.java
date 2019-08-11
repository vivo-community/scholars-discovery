package edu.tamu.scholars.middleware.discovery.response;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.FacetOptions.FacetSort;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
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

            switch (facetArgument.get().getType()) {
            case DATE_YEAR:
                entries = new ArrayList<FacetEntry>(entries.stream().<Map<Integer, FacetEntry>>collect(HashMap::new, (m, e) -> m.put(DateFormatUtility.parse(e.value).getYear(), e), Map::putAll).values());
                break;
            case STRING:
            default:
                break;
            }

            sort(entries, facetArgument.get());

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

    private static void sort(List<FacetEntry> entries, FacetArg facetArg) {
        FacetSortArg facetSort = facetArg.getSort();
        FacetSort property = facetSort.getProperty();
        Sort.Direction direction = facetSort.getDirection();
        if (property.equals(FacetSort.COUNT) && direction.equals(Sort.Direction.DESC)) {
            return;
        }
        Collections.sort(entries, new Comparator<FacetEntry>() {
            public int compare(FacetEntry e1, FacetEntry e2) {
                if (property.equals(FacetSort.COUNT)) {
                    return direction.equals(Direction.ASC) ? Long.compare(e1.count, e2.count) : Long.compare(e2.count, e1.count);
                }
                try {
                    LocalDate ld1 = DateFormatUtility.parse(e1.value);
                    LocalDate ld2 = DateFormatUtility.parse(e2.value);
                    return direction.equals(Direction.ASC) ? ld1.compareTo(ld2) : ld2.compareTo(ld1);
                } catch (DateTimeParseException dtpe) {
                    try {
                        Double d1 = Double.parseDouble(e1.value);
                        Double d2 = Double.parseDouble(e2.value);
                        return direction.equals(Direction.ASC) ? d1.compareTo(d2) : d2.compareTo(d1);
                    } catch (NumberFormatException nfe) {
                        return direction.equals(Direction.ASC) ? e1.value.compareTo(e2.value) : e2.value.compareTo(e1.value);
                    }
                }
            }
        });
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