package edu.tamu.scholars.middleware.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ExportTest {

    @Test
    public void testDefaultConstructor() {
        Export export = new Export();
        assertNotNull(export);
        assertEquals("||", export.getDelimiter());
    }

    @Test
    public void testGettersAndSetters() {
        Export export = new Export();

        export.setColumnHeader("Test");
        export.setValuePath("test");
        export.setDelimiter("::");

        assertEquals("Test", export.getColumnHeader());
        assertEquals("test", export.getValuePath());
        assertEquals("::", export.getDelimiter());
    }

}
