package edu.tamu.scholars.middleware.discovery.argument;

import org.springframework.data.domain.Sort;

import edu.tamu.scholars.middleware.view.model.FacetSort;

public class FacetSortArg {

    private final FacetSort property;

    private final Sort.Direction direction;

    FacetSortArg(String name, String direction) {
        this.property = FacetSort.valueOf(name.toUpperCase());
        this.direction = Sort.Direction.valueOf(direction.toUpperCase());
    }

    public FacetSort getProperty() {
        return property;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public static FacetSortArg of(String sort) {
        String[] parts = sort.split(",");
        return new FacetSortArg(parts[0], parts.length > 1 ? parts[1] : "DESC");
    }

    @Override
    public String toString() {
        return String.format("%s,%s", property, direction);
    }

}