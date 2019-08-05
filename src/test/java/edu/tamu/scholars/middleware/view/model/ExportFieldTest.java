package edu.tamu.scholars.middleware.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ExportFieldTest {

    @Test
    public void testDefaultConstructor() {
        ExportField export = new ExportField();
        assertNotNull(export);
    }

    @Test
    public void testGettersAndSetters() {
        ExportField export = new ExportField();

        export.setColumnHeader("Test");
        export.setValuePath("test");

        assertEquals("Test", export.getColumnHeader());
        assertEquals("test", export.getValuePath());
    }

}
