package edu.tamu.scholars.middleware.discovery.argument;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DEFAULT_QUERY;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;

// TODO: refactor to DiscoveryNetworkDescriptorArg and add argument resolver
public class DiscoveryNetworkDescriptor {

    private final String id;

    private final String dateField;

    private final List<String> dataFields;

    private final String typeFilter;

    private DiscoveryNetworkDescriptor(String id, String dateField, List<String> dataFields, String typeFilter) {
        super();
        this.id = id;
        this.dateField = dateField;
        this.dataFields = dataFields;
        this.typeFilter = typeFilter;
    }

    public String getId() {
        return id;
    }

    public String getDateField() {
        return dateField;
    }

    public List<String> getDataFields() {
        return dataFields;
    }

    public String getTypeFilter() {
        return typeFilter;
    }

    public String getSort() {
        return String.format("%s asc", dateField);
    }

    public String getFieldList() {
        List<String> fields = new ArrayList<>(getDataFields());
        fields.add(dateField);
        fields.add(ID);
        return String.join(",", fields);
    }

    public String getFilterQuery() {
        return String.format("syncIds:%s AND %s", id, typeFilter);
    }

    public SolrParams getSolrParams() {
        final Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("q", DEFAULT_QUERY);
        queryParamMap.put("rows", String.valueOf(Integer.MAX_VALUE));
        queryParamMap.put("sort", getSort());
        queryParamMap.put("fl", getFieldList());
        queryParamMap.put("fq", getFilterQuery());

        return new MapSolrParams(queryParamMap);
    }

    public static DiscoveryNetworkDescriptor of(String id, String dateField, List<String> dataFields, String typeFilter) {
        return new DiscoveryNetworkDescriptor(id, dateField, dataFields, typeFilter);
    }

}
