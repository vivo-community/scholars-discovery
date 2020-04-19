package edu.tamu.scholars.middleware.discovery.argument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class BoostArgTest {

    @Test
    public void testDefaultConstructor() {
        BoostArg boostArg = new BoostArg("title", 2.0f);
        assertNotNull(boostArg);
        assertEquals("title", boostArg.getField());
        assertEquals(2.0f, boostArg.getValue());
    }

    @Test
    public void testOfGraphQLParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("field", "title");
        params.put("value", 2.0f);
        BoostArg boostArg = BoostArg.of(params);
        assertNotNull(boostArg);
        assertEquals("title", boostArg.getField());
        assertEquals(2.0f, boostArg.getValue());
    }

    @Test
    public void testOfQueryParameter() {
        BoostArg boostArg = BoostArg.of("title,2.0");
        assertNotNull(boostArg);
        assertEquals("title", boostArg.getField());
        assertEquals(2.0f, boostArg.getValue());
    }

}
