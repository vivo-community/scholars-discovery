package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility;
import edu.tamu.scholars.middleware.view.model.FacetType;

public class FacetArg {

    private final String field;

    private final FacetSortArg sort;

    private final int pageSize;

    private final int pageNumber;

    private final FacetType type;

    private final String exclusionTag;

    private final String rangeStart;

    private final String rangeEnd;

    private final String rangeGap;

    public FacetArg(String field, String sort, int pageSize, int pageNumber, String type, String exclusionTag, String rangeStart, String rangeEnd, String rangeGap) {
        this.field = DiscoveryUtility.findProperty(field);
        this.sort = FacetSortArg.of(sort);
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.type = FacetType.valueOf(type);
        this.exclusionTag = exclusionTag;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.rangeGap = rangeGap;
    }

    public String getField() {
        return field;
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

    public FacetType getType() {
        return type;
    }

    public String getExclusionTag() {
        return exclusionTag;
    }

    public String getRangeStart() {
        return rangeStart;
    }

    public String getRangeEnd() {
        return rangeEnd;
    }

    public String getRangeGap() {
        return rangeGap;
    }

    public String getCommand() {
        return StringUtils.isEmpty(exclusionTag) ? field : String.format("{!ex=%s}%s", exclusionTag, field);
    }

    @SuppressWarnings("unchecked")
    public static FacetArg of(Object input) {
        Map<String, Object> facet = (Map<String, Object>) input;
        String field = (String) facet.get("field");
        String sort = (String) facet.get("sort");
        int pageSize = (int) facet.get("pageSize");
        int pageNumber = (int) facet.get("pageNumber");
        String type = (String) facet.get("type");
        String exclusionTag = facet.containsKey("exclusionTag") ? (String) facet.get("exclusionTag") : StringUtils.EMPTY;
        String rangeStart = facet.containsKey("rangeStart") ? (String) facet.get("rangeStart") : "0";
        String rangeEnd = facet.containsKey("rangeEnd") ? (String) facet.get("rangeEnd") : "100000";
        String rangeGap = facet.containsKey("rangeGap") ? (String) facet.get("rangeGap") : "100";
        return new FacetArg(field, sort, pageSize, pageNumber, type, exclusionTag, rangeStart, rangeEnd, rangeGap);
    }

    public static FacetArg of(String field, Optional<String> sort, Optional<String> pageSize, Optional<String> pageNumber, Optional<String> type, Optional<String> exclusionTag, Optional<String> rangeStart, Optional<String> rangeEnd, Optional<String> rangeGap) {
        String sortParam = sort.isPresent() ? sort.get() : "COUNT,DESC";
        int pageSizeParam = pageSize.isPresent() ? Integer.valueOf(pageSize.get()) : 10;
        int pageNumberParam = pageNumber.isPresent() ? Integer.valueOf(pageNumber.get()) : 1;
        String typeParam = type.isPresent() ? type.get() : "STRING";
        String exclusionTagParam = exclusionTag.isPresent() ? exclusionTag.get() : StringUtils.EMPTY;
        String rangeStartParam = rangeStart.isPresent() ? rangeStart.get() : "0";
        String rangeEndParam = rangeEnd.isPresent() ? rangeEnd.get() : "100000";
        String rangeGapParam = rangeGap.isPresent() ? rangeGap.get() : "100";
        return new FacetArg(field, sortParam, pageSizeParam, pageNumberParam, typeParam, exclusionTagParam, rangeStartParam, rangeEndParam, rangeGapParam);
    }

}
