package edu.tamu.scholars.middleware.export.service;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.EMPTY_STRING;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.NESTED_DELIMITER;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.config.model.ExportConfig;
import edu.tamu.scholars.middleware.discovery.exception.InvalidValuePathException;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.export.argument.ExportArg;

@Service
public class CsvExporter implements Exporter {

    private static final String TYPE = "csv";

    private static final String CONTENT_TYPE = "text/csv";

    private static final String CONTENT_DISPOSITION_TEMPLATE = "attachment; filename=%s.csv";

    private static final String DELIMITER = "||";

    @Autowired
    private ExportConfig config;

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String contentDisposition(String filename) {
        return String.format(CONTENT_DISPOSITION_TEMPLATE, filename);
    }

    @Override
    public String contentType() {
        return CONTENT_TYPE;
    }

    @Override
    public StreamingResponseBody streamSolrResponse(Cursor<Individual> cursor, List<ExportArg> export) {
        return outputStream -> {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] headers = getColumnHeaders(export);
            try (CSVPrinter printer = new CSVPrinter(outputStreamWriter, CSVFormat.DEFAULT.withHeader(headers))) {
                while (cursor.hasNext()) {
                    Individual document = cursor.next();
                    List<String> properties = export.stream().map(e -> e.getField()).collect(Collectors.toList());
                    List<Object> row = getRow(document, properties.toArray(new String[properties.size()]));
                    printer.printRecord(row.toArray(new Object[row.size()]));
                }
                printer.flush();
            } catch (InvalidValuePathException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                outputStreamWriter.close();
                cursor.close();
            }
        };
    }

    private String[] getColumnHeaders(List<ExportArg> export) {
        List<String> columnHeaders = new ArrayList<String>();
        for (ExportArg exp : export) {
            columnHeaders.add(exp.getLabel());
        }
        return columnHeaders.toArray(new String[columnHeaders.size()]);
    }

    private List<Object> getRow(Individual document, String[] properties) throws InvalidValuePathException, IllegalArgumentException, IllegalAccessException {
        Map<String, List<String>> content = document.getContent();
        List<Object> row = new ArrayList<Object>();
        for (String property : properties) {
            if (property.equals(config.getIndividualKey())) {
                row.add(String.format("%s/%s", config.getIndividualBaseUri(), document.getId()));
                continue;
            }
            List<String> values = content.get(property);
            String value = values.isEmpty() ? EMPTY_STRING : String.join(DELIMITER, values.stream().map(this::removeNestedIdentifiers).collect(Collectors.toList()));
            row.add(removeNestedIdentifiers(value));
        }
        return row;
    }

    private String removeNestedIdentifiers(String value) {
        return value.contains(NESTED_DELIMITER) ? value.substring(0, value.indexOf(NESTED_DELIMITER)) : value;
    }

}
