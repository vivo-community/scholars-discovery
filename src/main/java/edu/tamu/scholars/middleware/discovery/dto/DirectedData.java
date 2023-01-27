package edu.tamu.scholars.middleware.discovery.dto;

import java.util.HashSet;
import java.util.Set;

public class DirectedData {

    private final Set<String> ids;

    private final String source;

    private final String target;

    private Integer count;

    private DirectedData(String id, String source, String target) {
        this.ids = new HashSet<>();
        this.ids.add(id);
        this.source = source;
        this.target = target;
        this.count = 1;
    }

    public Set<String> getIds() {
        return ids;
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

    public void add(String id) {
        // count unique connection for id
        if (this.ids.add(id)) {
            count++;
        }
    }

    public static DirectedData of(String id, String source, String target) {
        return new DirectedData(id, source, target);
    }

}
