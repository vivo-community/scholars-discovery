package edu.tamu.scholars.middleware.discovery.dto;

import java.util.Objects;

public class DirectedData {

    private final String source;

    private final String target;

    private Integer count;

    public DirectedData(String source, String target) {
        this.source = source;
        this.target = target;
        this.count = 1;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public Integer getCount() {
        return count;
    }

    public static DirectedData of(String source, String target) {
        return new DirectedData(source, target);
    }

    public DirectedData total(Integer count) {
        this.count = count;

        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DirectedData other = (DirectedData) obj;

        return Objects.equals(source, other.source) && Objects.equals(target, other.target);
    }

}
