package edu.tamu.scholars.middleware.graphql.generator;

import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.getDiscoveryDocumentTypes;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.FileSystemUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ExtendWith(SpringExtension.class)
public class NestedDocumentGeneratorTest {

    private final String destinationPath = "target/generated-sources";
    private final String destinationPackage = "edu.tamu.scholars.middleware.graphql.model";

    private File generatedModelDirectory;

    @BeforeEach
    public void setup() {
        generatedModelDirectory = new File(String.format("%s%s%s", destinationPath, File.separator, destinationPackage.replace(".", "/")));
        assertFalse(generatedModelDirectory.exists());
    }

    @Test
    public void testGenerate() throws JsonParseException, JsonMappingException, IOException {
        new NestedDocumentGenerator(destinationPath, destinationPackage).generate();
        assertFilesCreated();
    }

    @Test
    public void testGenerator() {
        NestedDocumentGenerator.main(new String[] { destinationPath, destinationPackage });
        assertFilesCreated();
    }

    private void assertFilesCreated() {
        assertTrue(generatedModelDirectory.exists());
        assertTrue(generatedModelDirectory.isDirectory());
        for (Class<?> docType : getDiscoveryDocumentTypes()) {
            File docFile = new File(String.format("%s%s%s.java", generatedModelDirectory.getAbsolutePath(), File.separator, docType.getSimpleName()));
            assertTrue(docFile.exists());
            assertTrue(docFile.isFile());
        }
    }

    @AfterEach
    public void cleanup() {
        FileSystemUtils.deleteRecursively(generatedModelDirectory);
    }

}
