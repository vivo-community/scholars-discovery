package edu.tamu.scholars.middleware.view.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;

@MappedSuperclass
public abstract class FieldView extends View {

    private static final long serialVersionUID = 2384987522208517634L;

    @Column(nullable = false)
    public String field;

    @Min(1)
    @Column(name = "\"order\"", nullable = false)
    private int order;

    @ElementCollection
    private List<Filter> filters;

    @ElementCollection
    private List<Sort> sort;

    public FieldView() {
        filters = new ArrayList<Filter>();
        sort = new ArrayList<Sort>();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

}
