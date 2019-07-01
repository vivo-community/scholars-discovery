package edu.tamu.scholars.middleware.discovery.generator;

import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.getDiscoveryDocumentTypes;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.FileSystemUtils;

@ExtendWith(SpringExtension.class)
public class NestedDocumentGeneratorTest {

    private final String destinationPath = "target/generated-sources";
    private final String destinationPackage = "edu.tamu.scholars.middleware.discovery.model.generated";

    private File generatedModelDirectory;

    @BeforeEach
    public void setup() {
        generatedModelDirectory = new File(String.format("%s%s%s", destinationPath, File.separator, destinationPackage.replace(".", "/")));
        assertFalse(generatedModelDirectory.exists());
    }

    @Test
    public void testGenerate() {
        NestedDocumentGenerator generator = new NestedDocumentGenerator(destinationPath, destinationPackage);
        generator.generate();
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
