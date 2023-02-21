package edu.tamu.scholars.middleware.discovery.argument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.tamu.scholars.middleware.view.model.FacetSort;

@ExtendWith(SpringExtension.class)
public class FacetSortArgTest {

    @Test
    public void testDefaultConstructor() {
        FacetSortArg facetSortArg = new FacetSortArg("COUNT", "DESC");
        assertNotNull(facetSortArg);
        assertEquals(FacetSort.COUNT, facetSortArg.getProperty());
        assertEquals(Direction.DESC, facetSortArg.getDirection());
    }

    @Test
    public void testOfQueryParameter() {
        FacetSortArg facetSortArg = FacetSortArg.of("COUNT,DESC");
        assertNotNull(facetSortArg);
        assertEquals(FacetSort.COUNT, facetSortArg.getProperty());
        assertEquals(Direction.DESC, facetSortArg.getDirection());
    }

    @Test
    public void testToString() {
        FacetSortArg facetSortArg = FacetSortArg.of("COUNT,DESC");
        assertEquals("COUNT,DESC", facetSortArg.toString());
    }

}
