package edu.tamu.scholars.middleware.discovery.service.export;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.EMPTY_STRING;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.NESTED_DELIMITER;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.PATH_DELIMETER_REGEX;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.REQUEST_PARAM_DELIMETER;
import static edu.tamu.scholars.middleware.discovery.utility.DiscoveryUtility.findField;

import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tamu.scholars.middleware.discovery.exception.InvalidValuePathException;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;

@Service
public class CSVExporter implements Exporter {

    private static final String TYPE = "csv";

    private static final String CONTENT_DISPOSITION = "attachment; filename=results.csv";

    private static final String DEFAULT_DELIMITER = "||";

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String contentDisposition() {
        return CONTENT_DISPOSITION;
    }

    @Override
    public MediaType mediaType() {
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    @Override
    public <D extends AbstractSolrDocument> StreamingResponseBody streamSolrResponse(Cursor<D> cursor, String[] exports) {
        return outputStream -> {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] headers = getColumnHeaders(exports);
            try (CSVPrinter printer = new CSVPrinter(outputStreamWriter, CSVFormat.DEFAULT.withHeader(headers))) {
                while (cursor.hasNext()) {
                    D document = cursor.next();
                    List<Object> row = new ArrayList<Object>();
                    for (String export : exports) {
                        String[] parts = export.split(REQUEST_PARAM_DELIMETER);
                        String valuePath = parts[0];
                        String delimiter = parts.length > 2 ? parts[2] : DEFAULT_DELIMITER;
                        Field field = findField(document.getClass(), valuePath.split(PATH_DELIMETER_REGEX));
                        Object objValue = ReflectionUtil.getFieldValue(field, document);
                        String value = EMPTY_STRING;
                        if (String.class.isInstance(objValue)) {
                            value = (String) objValue;
                        } else if (Collection.class.isInstance(objValue)) {
                            // @formatter:off
                            Collection<String> values = mapper.convertValue(objValue, new TypeReference<Collection<String>>() {});
                            // @formatter:on
                            value = String.join(delimiter, values.stream().map(this::removeNestedIdentifiers).collect(Collectors.toList()));
                        }
                        if (value == null) {
                            value = EMPTY_STRING;
                        }
                        row.add(removeNestedIdentifiers(value));
                    }
                    printer.printRecord(row.toArray(new Object[row.size()]));
                }
            } catch (InvalidValuePathException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        };
    }

    private String[] getColumnHeaders(String[] exports) {
        List<String> columnHeaders = new ArrayList<String>();
        for (String export : exports) {
            String[] parts = export.split(REQUEST_PARAM_DELIMETER);
            columnHeaders.add(parts.length > 1 ? parts[1] : parts[0]);
        }
        return columnHeaders.toArray(new String[columnHeaders.size()]);
    }

    private String removeNestedIdentifiers(String value) {
        return value.contains(NESTED_DELIMITER) ? value.substring(0, value.indexOf(NESTED_DELIMITER)) : value;
    }

}
