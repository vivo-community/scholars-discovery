package edu.tamu.scholars.middleware.service;

import static edu.tamu.scholars.middleware.auth.RegistrationTestUtility.getMockRegistration;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import edu.tamu.scholars.middleware.auth.controller.request.Registration;

@ExtendWith(SpringExtension.class)
public class TemplateServiceTest {

    @TestConfiguration
    static class TemplateServiceTestContextConfiguration {

        @Bean
        public TemplateService templateService() {
            return new TemplateService();
        }

        @Bean
        public ResourceService resourceService() {
            return new ResourceService();
        }

    }

    @Autowired
    private TemplateService templateService;

    @Test
    public void testTemplateConfirmRegistrationMessage() {
        ReflectionTestUtils.setField(templateService, "uiUrl", "http://localhost:4200");
        Registration registration = getMockRegistration("Bob", "Boring", "bboring@mailinator.com");
        String actual = templateService.templateConfirmRegistrationMessage(registration, "MTU0NTg1NTI3MDMzMzo3YWFiMWRlYjNkNTRkM2M0NGMxYmI4ODZjNDIyMjEzYzk2NjBkOGIxNDgwMmJjNGY0YjBkYzdiMmE4YWVkMWRmOnsiZmlyc3ROYW1lIjoiQm9iIiwibGFzdE5hbWUiOiJCb3JpbmciLCJlbWFpbCI6ImJib3JpbmdAbWFpbGluYXRvci5jb20ifTpkOTAyZjNlZWZhMDJlYzRiM2M0NDE3ZDFiYjYwMmI5ZTlkYWUxODU1OGUzNGExN2I5NWI1NDdjYjNmODIxZmNmYWZhNDA3ZDI0NmUwOWQ2YTAyMmJmZGI2OGUzODkwNzlhNDRhNjVmZTMyNjlmY2M1M2FlOTUzM2U0ZTE2ZDE4Yg==");
        String expected = Files.contentOf(new File("src/test/resources/mock/templates/email/confirm-registration.html"), StandardCharsets.UTF_8);
        assertEquals(StringUtils.normalizeSpace(expected), StringUtils.normalizeSpace(actual));
    }

    @Test
    public void testTemplateSparql() {
        String actual = templateService.templateSparql("collection", "http://xmlns.com/foaf/0.1/Person");
        String expected = Files.contentOf(new File("src/test/resources/mock/templates/sparql/collection.sparql"), StandardCharsets.UTF_8);
        assertEquals(StringUtils.normalizeSpace(expected), StringUtils.normalizeSpace(actual));
    }

}
