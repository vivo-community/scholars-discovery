package edu.tamu.scholars.middleware.discovery.controller;

import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.discovery.assembler.AbstractSolrDocumentResourceAssembler;
import edu.tamu.scholars.middleware.discovery.assembler.FacetPagedResourcesAssembler;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.Concept;
import edu.tamu.scholars.middleware.discovery.model.Person;
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;
import edu.tamu.scholars.middleware.discovery.resource.AbstractSolrDocumentResource;

public abstract class AbstractSolrDocumentController<D extends AbstractSolrDocument, SDR extends SolrDocumentRepo<D>, R extends AbstractSolrDocumentResource<D>, SDA extends AbstractSolrDocumentResourceAssembler<D, R>> {

    @Autowired
    private SDR repo;

    @Autowired
    private SDA assembler;

    @Autowired
    private FacetPagedResourcesAssembler<D> pagedResourcesAssembler;

    @GetMapping("/search/facet")
    // @formatter:off
    public ResponseEntity<PagedResources<R>> search(
        @RequestParam(value = "query", required = false) String query,
        @RequestParam(value = "index", required = false) String index,
        @RequestParam(value = "facets", required = false) String[] facets,
        @RequestParam MultiValueMap<String, String> params,
        @PageableDefault Pageable pageable
    ) {
    // @formatter:on
        FacetPage<D> page = repo.search(query, index, facets, params, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toResource(page, assembler));
    }

    @GetMapping("/search/export")
    // @formatter:off
    public ResponseEntity<StreamingResponseBody> export(
        @RequestParam(value = "query", required = false) String query,
        @RequestParam(value = "index", required = false) String index,
        @RequestParam(value = "fields", required = false) String[] fields,
        @RequestParam MultiValueMap<String, String> params
    ) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=results.csv")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(outputStream -> {
                // TODO: add parameter to request for sort, add sort to stream function call
                Cursor<D> cursor = repo.stream(query, index, fields, params);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                // TODO: add parameter to request to identify CollectionView
                // TODO: create CSVPrinter with array of headers defined by the newly created exportFields CollectionView property
                try (CSVPrinter printer = new CSVPrinter(outputStreamWriter, CSVFormat.DEFAULT.withHeader("id", "name"))) {
                    while(cursor.hasNext()) {
                        D document = cursor.next();

                        // TODO: remove these as they are just to test casting and calling getters!!
                        try {
                            Concept concept = (Concept) document;
                            System.out.println(String.format("%s,%s", concept.getId(), concept.getName()));
                        } catch(Exception e) { }
                        try {
                            Person person = (Person) document;
                            System.out.println(String.format("%s,%s", person.getId(), person.getName()));
                        } catch(Exception e) { }

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
            });
    }
    // @formatter:on

    @GetMapping(value = "/search/count", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @formatter:off
    public ResponseEntity<Count> count(
        @RequestParam(value = "query", required = false) String query,
        @RequestParam(value = "fields", required = false) String[] facets,
        @RequestParam MultiValueMap<String, String> params
    ) {
    // @formatter:on
        return ResponseEntity.ok(new Count(repo.count(query, facets, params)));
    }

    class Count {
        private final long value;

        public Count(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }

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
