package edu.tamu.scholars.middleware.discovery.response;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.NESTED_DELIMITER;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DiscoveryNetwork {

    private final String id;

    private final Map<String, String> lookup;

    private final Map<String, Integer> linkCounts;

    private final Map<String, Integer> yearCounts;

    private final List<DirectedData> data;

    private DiscoveryNetwork(String id) {
        this.id = id;
        lookup = new HashMap<>();
        linkCounts = new HashMap<>();
        yearCounts = new HashMap<>();
        data = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getLookup() {
        return lookup;
    }

    public Map<String, Integer> getLinkCounts() {
        return linkCounts.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<String, Integer> getYearCounts() {
        return yearCounts.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public List<DirectedData> getData() {
        return data.stream()
            .sorted(Comparator.comparingInt(DirectedData::getCount).reversed())
            .collect(Collectors.toList());
    }

    public void index(String value) {
        String[] parts = value.split(NESTED_DELIMITER);
        if (parts.length > 1) {
            lookup.put(parts[1], parts[0]);
        }
    }

    public void countLink(String value) {
        String[] parts = value.split(NESTED_DELIMITER);
        if (parts.length > 1) {
            Integer count = linkCounts.containsKey(parts[1]) ? linkCounts.get(parts[1]) : 0;
            linkCounts.put(parts[1], ++count);
        }
    }

    public void countYear(String year) {
        Integer count = yearCounts.containsKey(year) ? yearCounts.get(year) : 0;
        yearCounts.put(year, ++count);
    }

    public synchronized void map(String id, String source, String target) {
        if (source.equals(target)) {
            return;
        }

        String[] sParts = source.split(NESTED_DELIMITER);
        String[] tParts = target.split(NESTED_DELIMITER);

        if (sParts.length <= 1 || tParts.length <= 1) {
            return;
        }

        String sId = sParts[1];
        String tId = tParts[1];

        for (DirectedData dd : this.data) {
            // count either direction
            if ((dd.getSource().equals(sId) && dd.getTarget().equals(tId)) ||
                (dd.getSource().equals(tId) && dd.getTarget().equals(sId))) {
                dd.add(id);
                return;
            }
        }
        // add new connection
        this.data.add(new DirectedData(id, sId, tId));
    }

    public static DiscoveryNetwork to(String id) {
        return new DiscoveryNetwork(id);
    }

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

    }

}
