package edu.tamu.scholars.middleware.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class DisplaySectionViewTest {

    @Test
    public void testDefaultConstructor() {
        DisplaySectionView section = new DisplaySectionView();
        assertNotNull(section);
        assertFalse(section.isHidden());
        assertFalse(section.isShared());
        assertEquals(0, section.getRequiredFields().size());
        assertEquals(0, section.getLazyReferences().size());
    }

    @Test
    public void testGettersAndSetters() {
        DisplaySectionView section = new DisplaySectionView();

        section.setName("Test");
        assertEquals("Test", section.getName());

        section.setField("name");
        assertEquals("name", section.getField());

        section.setOrder(1);
        assertEquals(1, section.getOrder());

        Filter filter = new Filter();
        filter.setField("type");
        filter.setValue("Test");

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(filter);

        section.setFilters(filters);

        Sort sort = new Sort();
        sort.setField("date");
        sort.setDirection(Direction.DESC);
        sort.setDate(true);

        List<Sort> sorting = new ArrayList<Sort>();
        sorting.add(sort);

        section.setSort(sorting);

        section.setPageSize(10);

        section.setHidden(true);
        assertTrue(section.isHidden());

        section.setShared(true);
        assertTrue(section.isShared());

        assertEquals(1, section.getFilters().size());
        assertEquals("type", section.getFilters().get(0).getField());
        assertEquals("Test", section.getFilters().get(0).getValue());

        assertEquals(1, section.getSort().size());
        assertEquals("date", section.getSort().get(0).getField());
        assertEquals(Direction.DESC, section.getSort().get(0).getDirection());
        assertTrue(section.getSort().get(0).isDate());

        assertEquals(10, section.getPageSize());

        section.setTemplate("<span>Hello, World!</span>");
        assertEquals("<span>Hello, World!</span>", section.getTemplate());

        List<String> requiredFields = new ArrayList<String>();
        requiredFields.add("type");

        section.setRequiredFields(requiredFields);
        assertEquals(1, section.getRequiredFields().size());
        assertEquals("type", section.getRequiredFields().get(0));

        List<String> lazyReferences = new ArrayList<String>();
        lazyReferences.add("publications");

        section.setLazyReferences(lazyReferences);
        assertEquals(1, section.getLazyReferences().size());
        assertEquals("publications", section.getLazyReferences().get(0));
    }

}
