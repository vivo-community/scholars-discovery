package edu.tamu.scholars.middleware.config.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.tamu.scholars.middleware.discovery.component.jena.TriplestoreHarvester;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.model.Collection;
import edu.tamu.scholars.middleware.discovery.model.Concept;
import edu.tamu.scholars.middleware.discovery.model.Document;
import edu.tamu.scholars.middleware.discovery.model.Organization;
import edu.tamu.scholars.middleware.discovery.model.Person;
import edu.tamu.scholars.middleware.discovery.model.Process;
import edu.tamu.scholars.middleware.discovery.model.Relationship;

@ExtendWith(SpringExtension.class)
public class HarvesterConfigTest {

    @Test
    public void testDefaultConstructor() {
        HarvesterConfig harvesterConfig = new HarvesterConfig();
        assertNotNull(harvesterConfig);
        assertEquals(TriplestoreHarvester.class, harvesterConfig.getType());
        assertNotNull(harvesterConfig.getDocumentTypes());
        assertEquals(0, harvesterConfig.getDocumentTypes().size());
    }

    @Test
    public void testTypeGetterSetter() {
        HarvesterConfig harvesterConfig = new HarvesterConfig();
        harvesterConfig.setType(TriplestoreHarvester.class);
        assertEquals(TriplestoreHarvester.class, harvesterConfig.getType());
    }

    @Test
    public void testDocumentTypesGetterSetter() {
        HarvesterConfig harvesterConfig = new HarvesterConfig();
        List<Class<? extends AbstractIndexDocument>> documentTypes = new ArrayList<Class<? extends AbstractIndexDocument>>();
        documentTypes.add(Collection.class);
        documentTypes.add(Concept.class);
        documentTypes.add(Document.class);
        documentTypes.add(Organization.class);
        documentTypes.add(Person.class);
        documentTypes.add(Process.class);
        documentTypes.add(Relationship.class);
        harvesterConfig.setDocumentTypes(documentTypes);
        assertEquals(7, harvesterConfig.getDocumentTypes().size());
    }

}
