package edu.tamu.scholars.middleware.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Cacheable("templates")
    public String getTemplate(String path) throws IOException {
        return IOUtils.toString(getResource(path), StandardCharsets.UTF_8.name());
    }

    public InputStream getResource(String path) throws IOException {
        Resource resource = resourceLoader.getResource(path);
        return resource.getInputStream();
    }

}
