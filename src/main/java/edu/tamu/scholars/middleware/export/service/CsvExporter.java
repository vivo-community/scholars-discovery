package edu.tamu.scholars.middleware.export.service;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.NESTED_DELIMITER;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.config.model.ExportConfig;
import edu.tamu.scholars.middleware.discovery.exception.InvalidValuePathException;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.export.argument.ExportArg;
import reactor.core.publisher.Flux;

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
    public StreamingResponseBody streamIndividuals(Flux<Individual> individuals, List<ExportArg> export) {
        return outputStream -> {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] headers = getColumnHeaders(export);
            CSVFormat format = CSVFormat.Builder
                .create()
                .setHeader(headers)
                .build();
            try (CSVPrinter printer = new CSVPrinter(outputStreamWriter, format)) {
                individuals.subscribe(
                    individual -> {
                        List<String> properties = export.stream()
                            .map(e -> e.getField())
                            .collect(Collectors.toList());
                        try {
                            List<Object> row = getRow(individual, properties);
                            printer.printRecord(row.toArray(new Object[row.size()]));
                        } catch (IllegalArgumentException | IllegalAccessException | InvalidValuePathException | IOException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                        throw new RuntimeException("Failed attempting to stream individuals", error);
                    },
                    () -> {
                        try {
                            printer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                );
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                outputStreamWriter.close();
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

    private List<Object> getRow(Individual individual, List<String> properties) throws InvalidValuePathException, IllegalArgumentException, IllegalAccessException {
        Map<String, Collection<Object>> content = individual.getContent();
        List<Object> row = new ArrayList<Object>();
        for (String property : properties) {
            if (property.equals(config.getIndividualKey())) {
                row.add(String.format("%s/%s", config.getIndividualBaseUri(), individual.getId()));
                continue;
            }
            String value = StringUtils.EMPTY;
            if (content.containsKey(property)) {
            	Collection<Object> values = content.get(property);
                if (values.size() > 0) {
                    value = String.join(DELIMITER, values.stream().map(this::serialize).collect(Collectors.toList()));
                }
            }
            row.add(serialize(value));
        }
        return row;
    }

    private String serialize(Object obj) {
        String value = String.valueOf(obj);
        return value.contains(NESTED_DELIMITER)
            ? value.substring(0, value.indexOf(NESTED_DELIMITER))
            : value;
    }

}
