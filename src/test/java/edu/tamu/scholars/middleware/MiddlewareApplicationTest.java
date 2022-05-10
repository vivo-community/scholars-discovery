package edu.tamu.scholars.middleware;

import static edu.tamu.scholars.middleware.auth.AuthConstants.PASSWORD_DURATION_IN_DAYS;
import static edu.tamu.scholars.middleware.auth.AuthConstants.PASSWORD_MAX_LENGTH;
import static edu.tamu.scholars.middleware.auth.AuthConstants.PASSWORD_MIN_LENGTH;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.EXPORT_INDIVIDUAL_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import edu.tamu.scholars.middleware.auth.config.AuthConfig;
import edu.tamu.scholars.middleware.auth.config.PasswordConfig;
import edu.tamu.scholars.middleware.auth.config.TokenConfig;
import edu.tamu.scholars.middleware.config.model.ExportConfig;
import edu.tamu.scholars.middleware.config.model.HarvesterConfig;
import edu.tamu.scholars.middleware.config.model.HttpConfig;
import edu.tamu.scholars.middleware.config.model.IndexerConfig;
import edu.tamu.scholars.middleware.config.model.MailConfig;
import edu.tamu.scholars.middleware.config.model.MiddlewareConfig;
import edu.tamu.scholars.middleware.discovery.service.jena.TriplestoreHarvester;
import edu.tamu.scholars.middleware.discovery.service.solr.SolrIndexer;

@SpringBootTest
public class MiddlewareApplicationTest {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private MiddlewareConfig middlewareConfig;

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private PasswordConfig passwordConfig;

    @Autowired
    private TokenConfig tokenConfig;

    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private HttpConfig httpConfig;

    @Autowired
    private ExportConfig exportConfig;

    @Test
    public void contextLoads() {
        assertEquals("test", activeProfile);
    }

    @Test
    public void testStaticConstants() {
        assertEquals(180, PASSWORD_DURATION_IN_DAYS);
        assertEquals(8, PASSWORD_MIN_LENGTH);
        assertEquals(64, PASSWORD_MAX_LENGTH);
        assertEquals(64, PASSWORD_MAX_LENGTH);
        assertEquals("individual", EXPORT_INDIVIDUAL_KEY);
    }

    @Test
    public void testMiddlewareConfig() {
        AuthConfig authConfig = middlewareConfig.getAuth();
        assertEquals(14, authConfig.getRegistrationTokenDuration());

        PasswordConfig passwordConfig = authConfig.getPassword();
        assertEquals(180, passwordConfig.getDuration());
        assertEquals(8, passwordConfig.getMinLength());
        assertEquals(64, passwordConfig.getMaxLength());

        TokenConfig tokenConfig = authConfig.getToken();
        assertEquals(1, tokenConfig.getServerInteger());
        assertEquals("wKFkxTX54UzKx6xCYnC8WlEI2wtOy0PR", tokenConfig.getServerSecret());
        assertEquals(64, tokenConfig.getPseudoRandomNumberBytes());

        MailConfig mailConfig = middlewareConfig.getMail();
        assertEquals("scholarsdiscovery@gmail.com", mailConfig.getFrom());
        assertEquals("scholarsdiscovery@gmail.com", mailConfig.getReplyTo());

        HttpConfig httpConfig = middlewareConfig.getHttp();
        assertEquals(60000, httpConfig.getTimeout());
        assertEquals(60000, httpConfig.getTimeToLive());
        assertEquals(30000, httpConfig.getRequestTimeout());
        assertEquals(60000, httpConfig.getSocketTimeout());

        ExportConfig exportConfig = middlewareConfig.getExport();
        assertEquals("individual", exportConfig.getIndividualKey());
        assertEquals("http://localhost:4200/display", exportConfig.getIndividualBaseUri());

        List<HarvesterConfig> harvesterConfigs = middlewareConfig.getHarvesters();
        assertEquals(1, harvesterConfigs.size());
        HarvesterConfig harvesterConfig = harvesterConfigs.get(0);
        assertEquals(7, harvesterConfig.getDocumentTypes().size());
        assertEquals(TriplestoreHarvester.class, harvesterConfig.getType());

        List<IndexerConfig> indexerConfigs = middlewareConfig.getIndexers();
        assertEquals(1, indexerConfigs.size());
        IndexerConfig indexerConfig = indexerConfigs.get(0);
        assertEquals(7, indexerConfig.getDocumentTypes().size());
        assertEquals(SolrIndexer.class, indexerConfig.getType());
    }

    @Test
    public void testAuthConfig() {
        PasswordConfig passwordConfig = authConfig.getPassword();
        assertEquals(14, authConfig.getRegistrationTokenDuration());
        assertEquals(180, passwordConfig.getDuration());
        assertEquals(8, passwordConfig.getMinLength());
        assertEquals(64, passwordConfig.getMaxLength());
        TokenConfig tokenConfig = authConfig.getToken();
        assertEquals(1, tokenConfig.getServerInteger());
        assertEquals("wKFkxTX54UzKx6xCYnC8WlEI2wtOy0PR", tokenConfig.getServerSecret());
        assertEquals(64, tokenConfig.getPseudoRandomNumberBytes());
    }

    @Test
    public void testPasswordConfig() {
        assertEquals(180, passwordConfig.getDuration());
        assertEquals(8, passwordConfig.getMinLength());
        assertEquals(64, passwordConfig.getMaxLength());
    }

    @Test
    public void testTokenConfig() {
        assertEquals(1, tokenConfig.getServerInteger());
        assertEquals("wKFkxTX54UzKx6xCYnC8WlEI2wtOy0PR", tokenConfig.getServerSecret());
        assertEquals(64, tokenConfig.getPseudoRandomNumberBytes());
    }

    @Test
    public void testMailConfig() {
        assertEquals("scholarsdiscovery@gmail.com", mailConfig.getFrom());
        assertEquals("scholarsdiscovery@gmail.com", mailConfig.getReplyTo());
    }

    @Test
    public void testHttpConfig() {
        assertEquals(60000, httpConfig.getTimeout());
        assertEquals(60000, httpConfig.getTimeToLive());
        assertEquals(30000, httpConfig.getRequestTimeout());
        assertEquals(60000, httpConfig.getSocketTimeout());
    }

    @Test
    public void testExportConfig() {
        assertEquals("individual", exportConfig.getIndividualKey());
        assertEquals("http://localhost:4200/display", exportConfig.getIndividualBaseUri());
    }

    @Test
    public void testMain() {
        MiddlewareApplication.main(new String[0]);
    }

}
