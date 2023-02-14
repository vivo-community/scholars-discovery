package edu.tamu.scholars.middleware.view.model;

import static edu.tamu.scholars.middleware.view.ViewTestUtility.MOCK_VIEW_NAME;
import static edu.tamu.scholars.middleware.view.ViewTestUtility.getMockDiscoveryView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class DiscoveryViewTest {

    @Test
    public void testDefaultConstructor() {
        DiscoveryView discoveryView = new DiscoveryView();
        assertNotNull(discoveryView);
        assertNotNull(discoveryView.getFacets());
        assertNotNull(discoveryView.getFilters());
        assertTrue(discoveryView.getFacets().isEmpty());
        assertTrue(discoveryView.getFilters().isEmpty());
        assertTrue(discoveryView.getBoosts().isEmpty());
        assertTrue(discoveryView.getSort().isEmpty());
        assertTrue(discoveryView.getExport().isEmpty());
    }

    @Test
    public void testGettersAndSetters() {
        DiscoveryView discoveryView = getMockDiscoveryView();
        discoveryView.setId(1L);

        assertEquals(1L, discoveryView.getId(), 1);
        assertEquals(MOCK_VIEW_NAME, discoveryView.getName());
        assertEquals(Layout.GRID, discoveryView.getLayout());

        assertEquals("overview", discoveryView.getDefaultSearchField());

        assertEquals(1, discoveryView.getHighlightFields().size());
        assertEquals("overview", discoveryView.getHighlightFields().get(0));
        assertEquals("<em>", discoveryView.getHighlightPrefix());
        assertEquals("</em>", discoveryView.getHighlightPostfix());

        assertTrue(discoveryView.getTemplates().containsKey("default"));
        assertEquals("<h1>Person template from WSYWIG</h1>", discoveryView.getTemplates().get("default"));

        assertEquals(1, discoveryView.getStyles().size());
        assertEquals("color: maroon;", discoveryView.getStyles().get(0));

        assertEquals(1, discoveryView.getFields().size());
        assertEquals("title", discoveryView.getFields().get(0));

        assertEquals(1, discoveryView.getFacets().size());
        assertEquals("Name", discoveryView.getFacets().get(0).getName());
        assertEquals("name", discoveryView.getFacets().get(0).getField());
        assertEquals(FacetType.STRING, discoveryView.getFacets().get(0).getType());
        assertEquals(FacetSort.COUNT, discoveryView.getFacets().get(0).getSort());
        assertEquals(Sort.Direction.DESC, discoveryView.getFacets().get(0).getDirection());
        assertEquals(20, discoveryView.getFacets().get(0).getPageSize());
        assertEquals(1, discoveryView.getFacets().get(0).getPageNumber());
        assertTrue(discoveryView.getFacets().get(0).isCollapsed());
        assertFalse(discoveryView.getFacets().get(0).isHidden());

        assertEquals(1, discoveryView.getFilters().size());
        assertEquals("type", discoveryView.getFilters().get(0).getField());
        assertEquals("FacultyMember", discoveryView.getFilters().get(0).getValue());

        assertEquals(1, discoveryView.getBoosts().size());
        assertEquals("name", discoveryView.getBoosts().get(0).getField());
        assertEquals(2.0f, discoveryView.getBoosts().get(0).getValue());

        assertEquals(1, discoveryView.getSort().size());
        assertEquals("name", discoveryView.getSort().get(0).getField());
        assertEquals(Direction.ASC, discoveryView.getSort().get(0).getDirection());

        assertEquals(2, discoveryView.getExport().size());
        assertEquals("Id", discoveryView.getExport().get(0).getColumnHeader());
        assertEquals("id", discoveryView.getExport().get(0).getValuePath());
        assertEquals("Name", discoveryView.getExport().get(1).getColumnHeader());
        assertEquals("name", discoveryView.getExport().get(1).getValuePath());
    }

}
