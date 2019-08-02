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
public class ExportViewTest {

    @Test
    public void testDefaultConstructor() {
        ExportView export = new ExportView();
        assertNotNull(export);
        assertEquals(0, export.getRequiredFields().size());
        assertEquals(0, export.getLazyReferences().size());
        assertEquals(0, export.getFieldViews().size());
    }

    @Test
    public void testGettersAndSetters() {
        ExportView export = new ExportView();

        export.setName("Test");
        assertEquals("Test", export.getName());

        export.setContentTemplate("<html><body><span>Hello, Content!</span></body></html>");
        assertEquals("<html><body><span>Hello, Content!</span></body></html>", export.getContentTemplate());

        export.setHeaderTemplate("<html><body><span>Hello, Header!</span></body></html>");
        assertEquals("<html><body><span>Hello, Header!</span></body></html>", export.getHeaderTemplate());

        List<String> requiredFields = new ArrayList<String>();
        requiredFields.add("type");

        export.setRequiredFields(requiredFields);
        assertEquals(1, export.getRequiredFields().size());
        assertEquals("type", export.getRequiredFields().get(0));

        List<LazyReference> lazyReferences = new ArrayList<LazyReference>();
        LazyReference reference = new LazyReference();
        reference.setCollection("persons");
        reference.setField("publications");
        lazyReferences.add(reference);

        export.setLazyReferences(lazyReferences);
        assertEquals(1, export.getLazyReferences().size());
        assertEquals("publications", export.getLazyReferences().get(0).getField());
        assertEquals("persons", export.getLazyReferences().get(0).getCollection());

        List<ExportFieldView> fieldViews = new ArrayList<ExportFieldView>();

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

        fieldViews.add(exportField);

        export.setFieldViews(fieldViews);

        assertEquals(1, export.getFieldViews().size());

        assertEquals("Test", export.getFieldViews().get(0).getName());
        assertEquals("publications", export.getFieldViews().get(0).getField());

        assertEquals(1, export.getFieldViews().get(0).getFilters().size());
        assertEquals("type", export.getFieldViews().get(0).getFilters().get(0).getField());
        assertEquals("Test", export.getFieldViews().get(0).getFilters().get(0).getValue());

        assertEquals(1, export.getFieldViews().get(0).getSort().size());
        assertEquals("date", export.getFieldViews().get(0).getSort().get(0).getField());
        assertEquals(Direction.DESC, export.getFieldViews().get(0).getSort().get(0).getDirection());
        assertTrue(export.getFieldViews().get(0).getSort().get(0).isDate());

        assertEquals(10, export.getFieldViews().get(0).getLimit());
    }

}
