package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;

import org.springframework.data.solr.core.query.FacetOptions.FacetSort;

public class FacetArg extends MappingArg {

    private final FacetSort sort;

    private final int pageSize;

    private final int pageNumber;

    public FacetArg(String field, FacetSort sort, int pageSize, int pageNumber) {
        super(field);
        this.sort = sort;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public FacetSort getSort() {
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
        FacetSort sort = (FacetSort) facet.get("sort");
        Integer pageSize = (Integer) facet.get("pageSize");
        Integer pageNumber = (Integer) facet.get("pageNumber");
        return new FacetArg(field, sort, pageSize, pageNumber);
    }

    public static FacetArg of(String field) {
        return new FacetArg(field, FacetSort.COUNT, 10, 0);
    }

}
