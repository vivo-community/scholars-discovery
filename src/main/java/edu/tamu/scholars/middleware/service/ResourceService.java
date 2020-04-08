package edu.tamu.scholars.middleware.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    private static final Map<String, String> TEMPLATES = new HashMap<String, String>();

    @Autowired
    private ResourceLoader resourceLoader;

    public String getTemplate(String path) throws IOException {
        // NOTE: wold prefer to use @Cacheable, but could not get to work as expected
        if (TEMPLATES.containsKey(path)) {
            return TEMPLATES.get(path);
        }
        String template = IOUtils.toString(getResource(path), StandardCharsets.UTF_8.name());
        TEMPLATES.put(path, template);
        return template;
    }

    public InputStream getResource(String path) throws IOException {
        Resource resource = resourceLoader.getResource(path);
        return resource.getInputStream();
    }

}
