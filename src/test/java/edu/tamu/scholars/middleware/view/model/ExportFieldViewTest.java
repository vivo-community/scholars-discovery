package edu.tamu.scholars.middleware.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ExportFieldViewTest {

    @Test
    public void testDefaultConstructor() {
        ExportFieldView exportField = new ExportFieldView();
        assertNotNull(exportField);
        assertNotNull(exportField.getFilters());
        assertNotNull(exportField.getSort());
        assertTrue(exportField.getFilters().isEmpty());
        assertTrue(exportField.getSort().isEmpty());
        assertEquals(5, exportField.getLimit());
    }

    @Test
    public void testGettersAndSetters() {
        ExportFieldView exportField = new ExportFieldView();

        exportField.setName("Test");
        exportField.setField("publications");

        Filter filter = new Filter();
        filter.setField("type");
        filter.setValue("Test");

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(filter);

        exportField.setFilters(filters);

        Sort sort = new Sort();
        sort.setField("date");
        sort.setDirection(Direction.DESC);
        sort.setDate(true);

        List<Sort> sorting = new ArrayList<Sort>();
        sorting.add(sort);

        exportField.setSort(sorting);

        exportField.setLimit(10);

        assertEquals("Test", exportField.getName());
        assertEquals("publications", exportField.getField());

        assertEquals(1, exportField.getFilters().size());
        assertEquals("type", exportField.getFilters().get(0).getField());
        assertEquals("Test", exportField.getFilters().get(0).getValue());

        assertEquals(1, exportField.getSort().size());
        assertEquals("date", exportField.getSort().get(0).getField());
        assertEquals(Direction.DESC, exportField.getSort().get(0).getDirection());
        assertTrue(exportField.getSort().get(0).isDate());

        assertEquals(10, exportField.getLimit());
    }

}
