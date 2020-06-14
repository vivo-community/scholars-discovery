package edu.tamu.scholars.middleware.view.model;

import static edu.tamu.scholars.middleware.view.ViewTestUtility.MOCK_VIEW_NAME;
import static edu.tamu.scholars.middleware.view.ViewTestUtility.getMockDirectoryView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.tamu.scholars.middleware.model.OpKey;

@ExtendWith(SpringExtension.class)
public class DirectoryViewTest {

    @Test
    public void testDefaultConstructor() {
        DirectoryView directoryView = new DirectoryView();
        assertNotNull(directoryView);
        assertNotNull(directoryView.getFacets());
        assertNotNull(directoryView.getFilters());
        assertTrue(directoryView.getFacets().isEmpty());
        assertTrue(directoryView.getFilters().isEmpty());
        assertTrue(directoryView.getBoosts().isEmpty());
        assertTrue(directoryView.getSort().isEmpty());
        assertTrue(directoryView.getExport().isEmpty());
    }

    @Test
    public void testGettersAndSetters() {
        DirectoryView directoryView = getMockDirectoryView();
        directoryView.setId(1L);

        assertEquals(1L, directoryView.getId(), 1);
        assertEquals(MOCK_VIEW_NAME, directoryView.getName());
        assertEquals(Layout.LIST, directoryView.getLayout());

        assertTrue(directoryView.getTemplates().containsKey("default"));
        assertEquals("<h1>Person template from WSYWIG</h1>", directoryView.getTemplates().get("default"));

        assertEquals(1, directoryView.getStyles().size());
        assertEquals("color: maroon;", directoryView.getStyles().get(0));

        assertEquals(1, directoryView.getFields().size());
        assertEquals("title", directoryView.getFields().get(0));

        assertEquals(1, directoryView.getFacets().size());
        assertEquals("Name", directoryView.getFacets().get(0).getName());
        assertEquals("name", directoryView.getFacets().get(0).getField());
        assertEquals(FacetType.STRING, directoryView.getFacets().get(0).getType());
        assertEquals(FacetOptions.FacetSort.COUNT, directoryView.getFacets().get(0).getSort());
        assertEquals(Sort.Direction.DESC, directoryView.getFacets().get(0).getDirection());
        assertEquals(20, directoryView.getFacets().get(0).getPageSize());
        assertEquals(1, directoryView.getFacets().get(0).getPageNumber());
        assertTrue(directoryView.getFacets().get(0).isCollapsed());
        assertFalse(directoryView.getFacets().get(0).isHidden());

        assertEquals(1, directoryView.getFilters().size());
        assertEquals("type", directoryView.getFilters().get(0).getField());
        assertEquals("FacultyMember", directoryView.getFilters().get(0).getValue());

        assertEquals(1, directoryView.getBoosts().size());
        assertEquals("name", directoryView.getBoosts().get(0).getField());
        assertEquals(2.0f, directoryView.getBoosts().get(0).getValue());

        assertEquals(1, directoryView.getSort().size());
        assertEquals("name", directoryView.getSort().get(0).getField());
        assertEquals(Direction.ASC, directoryView.getSort().get(0).getDirection());

        assertNotNull(directoryView.getIndex());
        assertEquals("name", directoryView.getIndex().getField());
        assertEquals(OpKey.ENDS_WITH, directoryView.getIndex().getOpKey());

        assertEquals(2, directoryView.getExport().size());
        assertEquals("Id", directoryView.getExport().get(0).getColumnHeader());
        assertEquals("id", directoryView.getExport().get(0).getValuePath());
        assertEquals("Name", directoryView.getExport().get(1).getColumnHeader());
        assertEquals("name", directoryView.getExport().get(1).getValuePath());
    }

}
