package edu.tamu.scholars.middleware.discovery.service.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.DirectoryStream;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import reactor.core.publisher.Flux;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tamu.scholars.middleware.discovery.service.Harvester;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
/*
  config:
  
  harvesters:
  - type: edu.tamu.scholars.middleware.discovery.service.json.LocalJsonFileHarvester
    documentTypes:
    - edu.tamu.scholars.middleware.discovery.model.Person
*/
// source of json ... queue instead of directory?
public class LocalJsonFileHarvester implements Harvester {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Class<AbstractIndexDocument> type;

    // private final Class<AbstractNestedDocument> type;
    public LocalJsonFileHarvester(Class<AbstractIndexDocument> type) {
        this.type = type;
    }

    public Flux<AbstractIndexDocument> harvest() {
        String dataDirectory = "data/" + name();
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dataDirectory));
            return Flux.fromIterable(directoryStream)
            .map(this::subject)
            .map(this::harvest);
        } catch (IOException ex) {
           throw new RuntimeException(ex);
        }
    }

    public AbstractIndexDocument harvest(String subject) {
        try {
            return createDocument(subject);
        } catch (Exception e) {
            logger.error(String.format("Unable to index %s: %s", type.getSimpleName(),subject));
            logger.error(String.format("Error: %s", e.getMessage()));
            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
            throw new RuntimeException(e);
        }

    }

    public Class<AbstractIndexDocument> type() {
        return type;
    }

    private AbstractIndexDocument createDocument(String subject) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException,
            JsonProcessingException, IOException {
         
        AbstractIndexDocument document = construct();
        ObjectMapper objectMapper = new ObjectMapper();
        
        String json = new String ( Files.readAllBytes(Paths.get(subject)));

        AbstractIndexDocument doc = objectMapper.readValue(json, type);  
        BeanUtils.copyProperties(doc, document);
        return document;
    }

    private AbstractIndexDocument construct() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return type.getConstructor().newInstance(new Object[0]);
    }

    private String name() {
        return type.getSimpleName();
    }

    private String subject(Path path) {
        return path.toString();
    }
}
