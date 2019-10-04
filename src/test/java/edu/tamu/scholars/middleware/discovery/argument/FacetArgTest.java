package edu.tamu.scholars.middleware.discovery.argument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.FacetOptions.FacetSort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.tamu.scholars.middleware.view.model.FacetType;

@ExtendWith(SpringExtension.class)
public class FacetArgTest {

    @Test
    public void testDefaultConstructor() {
        FacetArg facetArg = new FacetArg("class", "COUNT,DESC", 10, 1, "STRING");
        assertNotNull(facetArg);
        assertEquals("class", facetArg.getPath());
        assertEquals("class", facetArg.getProperty());
        assertEquals(FacetSort.COUNT, facetArg.getSort().getProperty());
        assertEquals(Direction.DESC, facetArg.getSort().getDirection());
        assertEquals(10, facetArg.getPageSize());
        assertEquals(1, facetArg.getPageNumber());
        assertEquals(FacetType.STRING, facetArg.getType());
    }

    @Test
    public void testOfGraphQLParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("field", "class");
        params.put("sort", "COUNT");
        params.put("pageSize", 10);
        params.put("pageNumber", 1);
        params.put("type", "STRING");
        FacetArg facetArg = FacetArg.of(params);
        assertNotNull(facetArg);
        assertEquals("class", facetArg.getPath());
        assertEquals("class", facetArg.getProperty());
        assertEquals(FacetSort.COUNT, facetArg.getSort().getProperty());
        assertEquals(Direction.DESC, facetArg.getSort().getDirection());
        assertEquals(10, facetArg.getPageSize());
        assertEquals(1, facetArg.getPageNumber());
        assertEquals(FacetType.STRING, facetArg.getType());
    }

    @Test
    public void testOfQueryParameter() {
        Optional<String> sort = Optional.of("COUNT,DESC");
        Optional<String> pageSize = Optional.of("10");
        Optional<String> pageNumber = Optional.of("1");
        Optional<String> type = Optional.of("STRING");
        FacetArg facetArg = FacetArg.of("class", sort, pageSize, pageNumber, type);
        assertNotNull(facetArg);
        assertEquals("class", facetArg.getPath());
        assertEquals("class", facetArg.getProperty());
        assertEquals(FacetSort.COUNT, facetArg.getSort().getProperty());
        assertEquals(Direction.DESC, facetArg.getSort().getDirection());
        assertEquals(10, facetArg.getPageSize());
        assertEquals(1, facetArg.getPageNumber());
        assertEquals(FacetType.STRING, facetArg.getType());
    }

}
