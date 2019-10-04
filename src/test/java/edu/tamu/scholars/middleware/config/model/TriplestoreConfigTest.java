package edu.tamu.scholars.middleware.config.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.tamu.scholars.middleware.service.SDBTriplestore;
import edu.tamu.scholars.middleware.service.TDBTriplestore;

@ExtendWith(SpringExtension.class)
public class TriplestoreConfigTest {

    @Test
    public void testDefaultConstructor() {
        TriplestoreConfig triplestoreConfig = new TriplestoreConfig();
        assertNotNull(triplestoreConfig);
        assertEquals(TDBTriplestore.class, triplestoreConfig.getType());
        assertEquals("triplestore", triplestoreConfig.getDirectory());
        assertEquals("layout2/hash", triplestoreConfig.getLayoutType());
        assertEquals("MySQL", triplestoreConfig.getDatabaseType());
        assertNull(triplestoreConfig.getDatasourceUrl());
        assertNull(triplestoreConfig.getUsername());
        assertNull(triplestoreConfig.getPassword());
        assertTrue(triplestoreConfig.isJdbcStream());
        assertEquals(8, triplestoreConfig.getJdbcFetchSize());
        assertTrue(triplestoreConfig.isStreamGraphAPI());
        assertFalse(triplestoreConfig.isAnnotateGeneratedSQL());
    }

    @Test
    public void testGettersAndSetters() {
        TriplestoreConfig triplestoreConfig = new TriplestoreConfig();
        triplestoreConfig.setType(SDBTriplestore.class);
        assertEquals(SDBTriplestore.class, triplestoreConfig.getType());
        triplestoreConfig.setDirectory("vivo_data");
        assertEquals("vivo_data", triplestoreConfig.getDirectory());
        triplestoreConfig.setLayoutType("layout/hash");
        assertEquals("layout/hash", triplestoreConfig.getLayoutType());
        triplestoreConfig.setDatabaseType("PostgreSQL");
        assertEquals("PostgreSQL", triplestoreConfig.getDatabaseType());
        triplestoreConfig.setDatasourceUrl("jdbc://localhost:6541/test");
        assertEquals("jdbc://localhost:6541/test", triplestoreConfig.getDatasourceUrl());
        triplestoreConfig.setUsername("username");
        assertEquals("username", triplestoreConfig.getUsername());
        triplestoreConfig.setPassword("password");
        assertEquals("password", triplestoreConfig.getPassword());
        triplestoreConfig.setJdbcStream(false);
        assertFalse(triplestoreConfig.isJdbcStream());
        triplestoreConfig.setJdbcFetchSize(16);
        assertEquals(16, triplestoreConfig.getJdbcFetchSize());
        triplestoreConfig.setStreamGraphAPI(false);
        assertFalse(triplestoreConfig.isStreamGraphAPI());
        triplestoreConfig.setAnnotateGeneratedSQL(true);
        assertTrue(triplestoreConfig.isAnnotateGeneratedSQL());
    }

}
