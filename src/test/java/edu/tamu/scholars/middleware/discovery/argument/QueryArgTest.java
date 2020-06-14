package edu.tamu.scholars.middleware.discovery.argument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.tamu.scholars.middleware.discovery.DiscoveryConstants;

@ExtendWith(SpringExtension.class)
public class QueryArgTest {

    private String expression = "Test";
    private String defaultField = "_text_";
    private String minimumShouldMatch = "1";
    private String queryField = "title";
    private String boostQuery = "";
    private String fields = "name,overview";

    @Test
    public void testDefaultConstructor() {
        QueryArg queryArg = new QueryArg(expression, defaultField, minimumShouldMatch, queryField, boostQuery, fields);
        assertNotNull(queryArg);
        assertEquals(expression, queryArg.getExpression());
        assertEquals(defaultField, queryArg.getDefaultField());
        assertEquals(minimumShouldMatch, queryArg.getMinimumShouldMatch());
        assertEquals(queryField, queryArg.getQueryField());
        assertEquals(boostQuery, queryArg.getBoostQuery());
        assertEquals(fields, queryArg.getFields());
    }

    @Test
    public void testOfQueryParameter() {
        QueryArg queryArg = QueryArg.of(Optional.of(expression), Optional.of(defaultField), Optional.of(minimumShouldMatch), Optional.of(queryField), Optional.of(boostQuery), Optional.of(fields));
        assertNotNull(queryArg);
        assertEquals(expression, queryArg.getExpression());
        assertEquals(defaultField, queryArg.getDefaultField());
        assertEquals(minimumShouldMatch, queryArg.getMinimumShouldMatch());
        assertEquals(queryField, queryArg.getQueryField());
        assertEquals(boostQuery, queryArg.getBoostQuery());
        assertEquals(fields, queryArg.getFields());
    }

    @Test
    public void testOfDefaultQueryParameter() {
        QueryArg queryArg = QueryArg.of(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        assertNotNull(queryArg);
        assertEquals(DiscoveryConstants.DEFAULT_QUERY, queryArg.getExpression());
        assertEquals(StringUtils.EMPTY, queryArg.getDefaultField());
        assertEquals(StringUtils.EMPTY, queryArg.getMinimumShouldMatch());
        assertEquals(StringUtils.EMPTY, queryArg.getQueryField());
        assertEquals(StringUtils.EMPTY, queryArg.getBoostQuery());
        assertEquals(StringUtils.EMPTY, queryArg.getFields());
    }

}
