package edu.tamu.scholars.middleware.discovery.service;

import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;

@Service
public class CSVExporter implements Exporter {

    private final static String TYPE = "csv";

    private final static String CONTENT_DISPOSITION = "attachment; filename=results.csv";

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
    public <D extends AbstractSolrDocument> StreamingResponseBody streamSolrResponse(Cursor<D> cursor) {

        return outputStream -> {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            // TODO: add parameter to request to identify CollectionView
            // TODO: create CSVPrinter with array of headers defined by the newly created exportFields CollectionView property
            try (CSVPrinter printer = new CSVPrinter(outputStreamWriter, CSVFormat.DEFAULT.withHeader("id", "name"))) {
                while (cursor.hasNext()) {
                    D document = cursor.next();

                    // TODO: remove these as they are just to test casting and calling getters!!
//                    try {
//                        Concept concept = (Concept) document;
//                        System.out.println(String.format("%s,%s", concept.getId(), concept.getName()));
//                    } catch(Exception e) { }
//                    try {
//                        Person person = (Person) document;
//                        System.out.println(String.format("%s,%s", person.getId(), person.getName()));
//                    } catch(Exception e) { }

                    // TODO: write utility to extract field from a document providing the field path, e.g. name or position.organization.label

                    // TODO: iterate over CollectionView exportFields and extract values accordingly
                    List<Object> values = new ArrayList<Object>();

                    Field idField = findField(document.getClass(), "id");

                    Object idValue = ReflectionUtil.getFieldValue(idField, document);
                    values.add(idValue);

                    Field nameField = findField(document.getClass(), "name");
                    Object nameValue = ReflectionUtil.getFieldValue(nameField, document);
                    values.add(nameValue);

                    printer.printRecord(values.toArray(new Object[values.size()]));
                }
            } finally {
                cursor.close();
            }
        };
    }

    // TODO; move to utility
    public static Field findField(Class<?> clazz, String property) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(property);
        } catch (NoSuchFieldException | SecurityException e) {
            Class<?> superClazz = clazz.getSuperclass();
            if (superClazz != null) {
                field = findField(superClazz, property);
            }
        }
        return field;
    }

}
