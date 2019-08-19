package edu.tamu.scholars.middleware.view.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

        section.setHidden(true);
        assertTrue(section.isHidden());

        section.setShared(true);
        assertTrue(section.isShared());

        section.setTemplate("<span>Hello, World!</span>");
        assertEquals("<span>Hello, World!</span>", section.getTemplate());

        List<String> requiredFields = new ArrayList<String>();
        requiredFields.add("type");

        section.setRequiredFields(requiredFields);
        assertEquals(1, section.getRequiredFields().size());
        assertEquals("type", section.getRequiredFields().get(0));

        List<LazyReference> lazyReferences = new ArrayList<LazyReference>();
        LazyReference reference = new LazyReference();
        reference.setCollection("persons");
        reference.setField("publications");
        lazyReferences.add(reference);

        section.setLazyReferences(lazyReferences);
        assertEquals(1, section.getLazyReferences().size());
        assertEquals("publications", section.getLazyReferences().get(0).getField());
        assertEquals("persons", section.getLazyReferences().get(0).getCollection());
    }

}
