package edu.tamu.scholars.middleware.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class FacetTest {

    @Test
    public void testDefaultConstructor() {
        Facet facet = new Facet();
        assertNotNull(facet);
        assertEquals(10, facet.getPageSize());
        assertEquals(1, facet.getPageNumber());
        assertTrue(facet.isCollapsed());
        assertFalse(facet.isHidden());
    }

    @Test
    public void testGettersAndSetters() {
        Facet facet = new Facet();

        facet.setName("Test");
        facet.setField("test");
        facet.setType(FacetType.DATE_YEAR);
        facet.setSort(FacetOptions.FacetSort.INDEX);
        facet.setDirection(Sort.Direction.ASC);
        facet.setPageSize(5);
        facet.setPageNumber(2);
        facet.setCollapsed(false);
        facet.setHidden(true);

        facet.setRangeStart("0");
        facet.setRangeEnd("1000");
        facet.setRangeGap("10");

        assertEquals("Test", facet.getName());
        assertEquals("test", facet.getField());
        assertEquals(FacetType.DATE_YEAR, facet.getType());
        assertEquals(FacetOptions.FacetSort.INDEX, facet.getSort());
        assertEquals(Sort.Direction.ASC, facet.getDirection());
        assertEquals(5, facet.getPageSize());
        assertEquals(2, facet.getPageNumber());
        assertFalse(facet.isCollapsed());
        assertTrue(facet.isHidden());

        assertEquals("0", facet.getRangeStart());
        assertEquals("1000", facet.getRangeEnd());
        assertEquals("10", facet.getRangeGap());
    }

}
