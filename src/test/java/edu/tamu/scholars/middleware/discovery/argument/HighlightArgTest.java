package edu.tamu.scholars.middleware.discovery.argument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class HighlightArgTest {

    @Test
    public void testDefaultConstructor() {
        HighlightArg highlightArg = new HighlightArg(new String[] { "title" }, "<b>", "</b>");
        assertNotNull(highlightArg);
        assertEquals(1, highlightArg.getFields().length);
        assertEquals("title", highlightArg.getFields()[0]);
        assertEquals("<b>", highlightArg.getPrefix());
        assertEquals("</b>", highlightArg.getPostfix());
    }

    @Test
    public void testOfQueryParameter() {
        HighlightArg highlightArg = HighlightArg.of(new String[] { "title" }, Optional.of("<b>"), Optional.of("</b>"));
        assertNotNull(highlightArg);
        assertEquals(1, highlightArg.getFields().length);
        assertEquals("title", highlightArg.getFields()[0]);
        assertEquals("<b>", highlightArg.getPrefix());
        assertEquals("</b>", highlightArg.getPostfix());
    }

    @Test
    public void testOfDefaultQueryParameter() {
        HighlightArg highlightArg = HighlightArg.of(new String[] {}, Optional.empty(), Optional.empty());
        assertNotNull(highlightArg);
        assertEquals(0, highlightArg.getFields().length);
        assertEquals(StringUtils.EMPTY, highlightArg.getPrefix());
        assertEquals(StringUtils.EMPTY, highlightArg.getPostfix());
    }

}
