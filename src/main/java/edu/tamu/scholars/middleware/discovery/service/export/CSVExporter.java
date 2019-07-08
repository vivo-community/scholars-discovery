package edu.tamu.scholars.middleware.discovery.service.export;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.EMPTY_STRING;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.NESTED_DELIMITER;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.PATH_DELIMETER_REGEX;
import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.findField;

import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tamu.scholars.middleware.config.ExportConfig;
import edu.tamu.scholars.middleware.discovery.argument.Export;
import edu.tamu.scholars.middleware.discovery.exception.InvalidValuePathException;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;

@Service
public class CSVExporter implements Exporter {

    private static final String TYPE = "csv";

    private static final String CONTENT_TYPE = "text/csv";

    private static final String CONTENT_DISPOSITION = "attachment; filename=results.csv";

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ExportConfig config;

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String contentDisposition() {
        return CONTENT_DISPOSITION;
    }

    @Override
    public String contentType() {
        return CONTENT_TYPE;
    }

    @Override
    public <D extends AbstractSolrDocument> StreamingResponseBody streamSolrResponse(Cursor<D> cursor, List<Export> export) {
        return outputStream -> {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] headers = getColumnHeaders(export);
            try (CSVPrinter printer = new CSVPrinter(outputStreamWriter, CSVFormat.DEFAULT.withHeader(headers))) {
                while (cursor.hasNext()) {
                    D document = cursor.next();
                    List<Object> row = new ArrayList<Object>();
                    for (Export exp : export) {
                        if (exp.getField().equals(config.getIndividualKey())) {
                            Field idField = findField(document.getClass(), ID);
                            idField.setAccessible(true);
                            Object idValue = idField.get(document);
                            if (config.isIncludeCollection()) {
                                SolrDocument solrDocument = document.getClass().getAnnotation(SolrDocument.class);
                                String collection = solrDocument.collection();
                                row.add(String.format("%s/%s/%s", config.getIndividualBaseUri(), collection, idValue));
                            } else {
                                row.add(String.format("%s/%s", config.getIndividualBaseUri(), idValue));
                            }
                            continue;
                        }
                        Field field = findField(document.getClass(), exp.getField().split(PATH_DELIMETER_REGEX));
                        field.setAccessible(true);
                        Object objValue = field.get(document);
                        String value = EMPTY_STRING;
                        if (String.class.isInstance(objValue)) {
                            value = (String) objValue;
                        } else if (Collection.class.isInstance(objValue)) {
                            // @formatter:off
                            Collection<String> values = mapper.convertValue(objValue, new TypeReference<Collection<String>>() {});
                            // @formatter:on
                            value = String.join(exp.getDelimiter(), values.stream().map(this::removeNestedIdentifiers).collect(Collectors.toList()));
                        }
                        if (value == null) {
                            value = EMPTY_STRING;
                        }
                        row.add(removeNestedIdentifiers(value));
                    }
                    printer.printRecord(row.toArray(new Object[row.size()]));
                }
            } catch (InvalidValuePathException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        };
    }

    private String[] getColumnHeaders(List<Export> export) {
        List<String> columnHeaders = new ArrayList<String>();
        for (Export exp : export) {
            columnHeaders.add(exp.getLabel());
        }
        return columnHeaders.toArray(new String[columnHeaders.size()]);
    }

    private String removeNestedIdentifiers(String value) {
        return value.contains(NESTED_DELIMITER) ? value.substring(0, value.indexOf(NESTED_DELIMITER)) : value;
    }

}