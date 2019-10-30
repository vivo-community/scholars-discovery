package edu.tamu.scholars.middleware.discovery.argument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.tamu.scholars.middleware.model.OpKey;

@ExtendWith(SpringExtension.class)
public class FilterArgTest {

    @Test
    public void testDefaultConstructor() {
        FilterArg filterArg = new FilterArg("class", "Concept", OpKey.EQUALS);
        assertNotNull(filterArg);
        assertEquals("class", filterArg.getProperty());
        assertEquals("class", filterArg.getPath());
        assertEquals("Concept", filterArg.getValue());
        assertEquals(OpKey.EQUALS, filterArg.getOpKey());
    }

    @Test
    public void testOfGraphQLParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("field", "class");
        params.put("value", "Concept");
        params.put("opKey", OpKey.EQUALS.getKey());
        FilterArg filterArg = FilterArg.of(params);
        assertNotNull(filterArg);
        assertEquals("class", filterArg.getProperty());
        assertEquals("class", filterArg.getPath());
        assertEquals("Concept", filterArg.getValue());
        assertEquals(OpKey.EQUALS, filterArg.getOpKey());
    }

    @Test
    public void testOfQueryParameter() {
        Optional<String> value = Optional.of("Concept");
        Optional<String> opKey = Optional.of(OpKey.EQUALS.getKey());
        FilterArg filterArg = FilterArg.of("class", value, opKey);
        assertNotNull(filterArg);
        assertEquals("class", filterArg.getProperty());
        assertEquals("class", filterArg.getPath());
        assertEquals("Concept", filterArg.getValue());
        assertEquals(OpKey.EQUALS, filterArg.getOpKey());
    }

}
