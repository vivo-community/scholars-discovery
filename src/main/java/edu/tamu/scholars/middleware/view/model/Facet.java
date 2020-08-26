package edu.tamu.scholars.middleware.view.model;

import static edu.tamu.scholars.middleware.view.model.FacetType.STRING;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.solr.core.query.FacetOptions.FacetSort.COUNT;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.FacetOptions.FacetSort;

import edu.tamu.scholars.middleware.model.OpKey;

@Embeddable
@JsonInclude(Include.NON_NULL)
public class Facet {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String field;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OpKey opKey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FacetType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FacetSort sort;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Column(nullable = false)
    private int pageSize;

    @Column(nullable = false)
    private int pageNumber;

    @Column(nullable = false)
    private boolean collapsed;

    @Column(nullable = false)
    private boolean hidden;

    @Column(nullable = true)
    private String rangeStart;

    @Column(nullable = true)
    private String rangeEnd;

    @Column(nullable = true)
    private String rangeGap;

    public Facet() {
        opKey = OpKey.EQUALS;
        type = STRING;
        sort = COUNT;
        direction = DESC;
        pageSize = 10;
        pageNumber = 1;
        collapsed = true;
        hidden = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public OpKey getOpKey() {
        return opKey;
    }

    public void setOpKey(OpKey opKey) {
        this.opKey = opKey;
    }

    public FacetType getType() {
        return type;
    }

    public void setType(FacetType type) {
        this.type = type;
    }

    public FacetSort getSort() {
        return sort;
    }

    public void setSort(FacetSort sort) {
        this.sort = sort;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(String rangeStart) {
        this.rangeStart = rangeStart;
    }

    public String getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(String rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public String getRangeGap() {
        return rangeGap;
    }

    public void setRangeGap(String rangeGap) {
        this.rangeGap = rangeGap;
    }

}
