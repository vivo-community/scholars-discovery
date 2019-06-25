package edu.tamu.scholars.middleware.discovery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;
import edu.tamu.scholars.middleware.discovery.resource.AbstractSolrDocumentResource;
import edu.tamu.scholars.middleware.discovery.service.export.Exporter;

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
        @RequestParam(value = "export", required = true) String[] exports,
        @RequestParam MultiValueMap<String, String> params,
        @SortDefault Sort sort,
        Exporter exporter
    ) {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, exporter.contentDisposition())
            .contentType(exporter.mediaType())
            .body(exporter.streamSolrResponse(repo.stream(query, index, fields, params, sort), exports));
    }
    // @formatter:on

    @GetMapping(value = "/search/count", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // @formatter:off
    public ResponseEntity<Count> count(
        @RequestParam(value = "query", required = false) String query,
        @RequestParam(value = "fields", required = false) String[] fields,
        @RequestParam MultiValueMap<String, String> params
    ) {
    // @formatter:on
        return ResponseEntity.ok(new Count(repo.count(query, fields, params)));
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

}