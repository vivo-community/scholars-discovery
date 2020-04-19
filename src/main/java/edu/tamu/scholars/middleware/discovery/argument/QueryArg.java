package edu.tamu.scholars.middleware.discovery.argument;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import edu.tamu.scholars.middleware.discovery.DiscoveryConstants;
import edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility;

public class QueryArg {

    private final String expression;

    private final String defaultField;

    private final String minimumShouldMatch;

    private final String queryField;

    private final String boostQuery;

    private final String fields;

    public QueryArg(String expression, String defaultField, String minimumShouldMatch, String queryField, String boostQuery, String fields) {
        this.expression = expression;
        this.defaultField = DiscoveryUtility.processFields(defaultField);
        this.minimumShouldMatch = minimumShouldMatch;
        this.queryField = DiscoveryUtility.processFields(queryField);
        this.boostQuery = boostQuery;
        this.fields = DiscoveryUtility.processFields(fields);
    }

    public String getExpression() {
        return expression;
    }

    public String getDefaultField() {
        return defaultField;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public String getQueryField() {
        return queryField;
    }

    public String getBoostQuery() {
        return boostQuery;
    }

    public String getFields() {
        return fields;
    }

    public static QueryArg of(Optional<String> q, Optional<String> df, Optional<String> mm, Optional<String> qf, Optional<String> bq, Optional<String> fl) {
        String expression = q.isPresent() ? q.get() : DiscoveryConstants.DEFAULT_QUERY;
        String defaultField = df.isPresent() ? df.get() : StringUtils.EMPTY;
        String minimumShouldMatch = mm.isPresent() ? mm.get() : StringUtils.EMPTY;
        String queryField = qf.isPresent() ? qf.get() : StringUtils.EMPTY;
        String boostQuery = bq.isPresent() ? bq.get() : StringUtils.EMPTY;
        String field = fl.isPresent() ? fl.get() : StringUtils.EMPTY;
        return new QueryArg(expression, defaultField, minimumShouldMatch, queryField, boostQuery, field);
    }

}
