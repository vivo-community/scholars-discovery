package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;
import java.util.Optional;

public class FacetArg extends MappingArg {

    private final FacetSortArg sort;

    private final int pageSize;

    private final int pageNumber;

    public FacetArg(String field, String sort, int pageSize, int pageNumber) {
        super(field);
        this.sort = FacetSortArg.of(sort);
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public FacetSortArg getSort() {
        return sort;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    @SuppressWarnings("unchecked")
    public static FacetArg of(Object input) {
        Map<String, Object> facet = (Map<String, Object>) input;
        String field = (String) facet.get("field");
        String sort = (String) facet.get("sort");
        Integer pageSize = (Integer) facet.get("pageSize");
        Integer pageNumber = (Integer) facet.get("pageNumber");
        return new FacetArg(field, sort, pageSize, pageNumber);
    }

    public static FacetArg of(String field, Optional<String> sort, Optional<String> pageSize, Optional<String> pageNumber) {
        // @formatter:off
        return new FacetArg(field,
            sort.isPresent() ? sort.get() : "COUNT,DESC",
            pageSize.isPresent() ? Integer.valueOf(pageSize.get()) : 10,
            pageNumber.isPresent() ? Integer.valueOf(pageNumber.get()) : 0);
        // @formatter:on
    }

}
