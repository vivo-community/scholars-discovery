package edu.tamu.scholars.middleware.discovery.component.solr;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.COLLECTION;
import static edu.tamu.scholars.middleware.discovery.service.IndexService.CREATED_FIELDS;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.tamu.scholars.middleware.discovery.annotation.FieldType;
import edu.tamu.scholars.middleware.discovery.component.Indexer;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;

public class SolrIndexer implements Indexer {

    private static final Logger logger = LoggerFactory.getLogger(SolrIndexer.class);

    @Autowired
    private SolrClient solrClient;

    private final Class<AbstractIndexDocument> type;

    public SolrIndexer(Class<AbstractIndexDocument> type) {
        this.type = type;
    }

    @Override
    public void init() {
        for (Field field : FieldUtils.getFieldsListWithAnnotation(type, FieldType.class)) {
            FieldType indexed = field.getAnnotation(FieldType.class);

            String name = StringUtils.isNotEmpty(indexed.value())
                ? indexed.value()
                : field.getName();

            if (!indexed.readonly() && !CREATED_FIELDS.contains(name) && CREATED_FIELDS.add(name)) {
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
                    addFieldRequest.process(solrClient, COLLECTION);
                } catch (Exception e) {
                    logger.debug("Failed to add field", e);
                }

                if (indexed.copyTo().length > 0) {
                    try {
                        SchemaRequest.AddCopyField addCopyFieldRequest = new SchemaRequest.AddCopyField(name, Arrays.asList(indexed.copyTo()));
                        addCopyFieldRequest.process(solrClient, COLLECTION);
                    } catch (Exception e) {
                        logger.debug("Failed to add copy field", e);
                    }
                }
            }
        }
    }

    @Override
    public void index(Collection<AbstractIndexDocument> documents) {
        try {
            solrClient.addBeans(COLLECTION, documents);
            solrClient.commit(COLLECTION);
            logger.info(String.format("Saved %s batch of %s", name(), documents.size()));
        } catch (Exception e) {
            logger.warn("Failed to save batch. Attempting individually.", e);
            documents.stream().forEach(this::index);
        }
    }

    @Override
    public void index(AbstractIndexDocument document) {
        try {
            solrClient.addBean(COLLECTION, document);
            solrClient.commit(COLLECTION);
            logger.info(String.format("Saved %s with id %s", name(), document.getId()));
        } catch (Exception e) {
            logger.warn(String.format("Failed to save document with id %s", document.getId()), e);
        }
    }

    @Override
    public void optimize() {
        try {
            solrClient.optimize(COLLECTION);
        } catch (Exception e) {
            logger.warn(String.format("Failed to optimize index"), e);
        }
    }

    @Override
    public Class<AbstractIndexDocument> type() {
        return type;
    }

    private String name() {
        return type.getSimpleName();
    }

}
