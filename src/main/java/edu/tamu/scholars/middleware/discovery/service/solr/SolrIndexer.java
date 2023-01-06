package edu.tamu.scholars.middleware.discovery.service.solr;

import static edu.tamu.scholars.middleware.discovery.service.IndexService.CREATED_FIELDS;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;

import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.service.Indexer;

public class SolrIndexer implements Indexer {

    private static final Logger logger = LoggerFactory.getLogger(SolrIndexer.class);

    @Autowired
    private SolrTemplate solrTemplate;
 
    @Autowired
    private SolrClient solrClient;

    private final Class<AbstractIndexDocument> type;

    public SolrIndexer(Class<AbstractIndexDocument> type) {
        this.type = type;
    }

    public void init() {
        String collection = collection();

        for (Field field : FieldUtils.getFieldsListWithAnnotation(type, Indexed.class)) {
            Indexed indexed = field.getAnnotation(Indexed.class);

            String name = StringUtils.isNotEmpty(indexed.value())
                ? indexed.value()
                : field.getName();

            if (!CREATED_FIELDS.contains(name) && CREATED_FIELDS.add(name) && !indexed.readonly()) {

                Map<String, Object> fieldAttributes = new HashMap<String,Object>();

                fieldAttributes.put("type", indexed.type());
                fieldAttributes.put("stored", indexed.stored());
                fieldAttributes.put("indexed", indexed.searchable());
                fieldAttributes.put("required", indexed.required());

                if (StringUtils.isNotEmpty(indexed.defaultValue())) {
                    fieldAttributes.put("defaultValue", indexed.defaultValue());
                }

                fieldAttributes.put("multiValued", Collection.class.isAssignableFrom(field.getType()));

                fieldAttributes.put("name", name);

                try {
                    SchemaRequest.AddField addFieldRequest = new SchemaRequest.AddField(fieldAttributes);
                    addFieldRequest.process(solrClient, collection);
                } catch (Exception e) {
                    logger.error("Failed to add field", e);
                }

                if (indexed.copyTo().length > 0) {
                    try {
                        SchemaRequest.AddCopyField addCopyFieldRequest = new SchemaRequest.AddCopyField(name, Arrays.asList(indexed.copyTo()));
                        addCopyFieldRequest.process(solrClient, collection);
                    } catch (Exception e) {
                        logger.error("Failed to add copy field", e);
                    }
                }
            }

        }
    }

    public void index(Collection<AbstractIndexDocument> documents) {
        String collection = collection();
        try {
            solrTemplate.saveBeans(collection, documents);
            solrTemplate.commit(collection);
            logger.info(String.format("Saved %s batch of %s", name(), documents.size()));
        } catch (Exception e) {
            logger.warn("Failed to save batch. Attempting individually.");
            e.printStackTrace();
            documents.stream().forEach(this::index);
        }
    }

    public void index(AbstractIndexDocument document) {
        String collection = collection();
        try {
            solrTemplate.saveBean(collection, document);
            solrTemplate.commit(collection);
            logger.info(String.format("Saved %s with id %s", name(), document.getId()));
        } catch (Exception e) {
            logger.warn(String.format("Failed to save document with id %s", document.getId()));
            e.printStackTrace();
        }
    }

    public Class<AbstractIndexDocument> type() {
        return type;
    }

    private String name() {
        return type.getSimpleName();
    }

    private String collection() {
        return type.getAnnotation(SolrDocument.class).collection();
    }

}
