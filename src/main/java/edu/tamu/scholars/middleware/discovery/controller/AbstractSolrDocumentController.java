package edu.tamu.scholars.middleware.discovery.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.discovery.argument.Export;
import edu.tamu.scholars.middleware.discovery.argument.Facet;
import edu.tamu.scholars.middleware.discovery.argument.Filter;
import edu.tamu.scholars.middleware.discovery.argument.Index;
import edu.tamu.scholars.middleware.discovery.assembler.AbstractSolrDocumentResourceAssembler;
import edu.tamu.scholars.middleware.discovery.assembler.FacetPagedResourcesAssembler;
import edu.tamu.scholars.middleware.discovery.exception.UnknownExporterTypeException;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;
import edu.tamu.scholars.middleware.discovery.resource.AbstractSolrDocumentResource;
import edu.tamu.scholars.middleware.discovery.service.export.Exporter;
import edu.tamu.scholars.middleware.discovery.service.export.ExporterRegistry;

public abstract class AbstractSolrDocumentController<D extends AbstractSolrDocument, SDR extends SolrDocumentRepo<D>, R extends AbstractSolrDocumentResource<D>, SDA extends AbstractSolrDocumentResourceAssembler<D, R>> implements ResourceProcessor<Resource<D>> {

    @Autowired
    private SDR repo;

    @Autowired
    private SDA assembler;

    @Autowired
    private FacetPagedResourcesAssembler<D> pagedResourcesAssembler;

    @GetMapping("/search/facet")
    // @formatter:off
    public ResponseEntity<PagedResources<R>> search(
        @RequestParam(value = "query", required = false, defaultValue = "*:*") String query,
        @PageableDefault Pageable pageable,
        Optional<Index> index,
        List<Facet> facets,
        List<Filter> filters
    ) {
    // @formatter:on
        FacetPage<D> page = repo.search(query, index, facets, filters, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toResource(page, assembler));
    }

    @GetMapping("/search/export")
    // @formatter:off
    public ResponseEntity<StreamingResponseBody> export(
        @RequestParam(value = "type", required = false, defaultValue = "csv") String type,
        @RequestParam(value = "query", required = false, defaultValue = "*:*") String query,
        @SortDefault Sort sort,
        Optional<Index> index,
        List<Filter> filters,
        List<Export> export
    ) throws UnknownExporterTypeException {
        Exporter exporter = ExporterRegistry.getExporter(type);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, exporter.contentDisposition())
            .header(HttpHeaders.CONTENT_TYPE, exporter.contentType())
            .body(exporter.streamSolrResponse(repo.stream(query, index, filters, sort), export));
    }
    // @formatter:on

    @GetMapping("/{id}/export")
    // @formatter:off
    public ResponseEntity<StreamingResponseBody> export(
        @PathVariable String id,
        @RequestParam(value = "type", required = false, defaultValue = "docx") String type
    ) throws UnknownExporterTypeException, IllegalArgumentException, IllegalAccessException {
        // TODO: throw not found exception if not found
        D document = repo.findById(id).get();
        Exporter exporter = ExporterRegistry.getExporter(type);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, exporter.contentDisposition())
            .header(HttpHeaders.CONTENT_TYPE, exporter.contentType())
            .body(exporter.streamIndividual(document));
    }
    // @formatter:on

    @GetMapping("/search/count")
    public ResponseEntity<Count> count(@RequestParam(value = "query", required = false, defaultValue = "*:*") String query, List<Filter> filters) {
        return ResponseEntity.ok(new Count(repo.count(query, filters)));
    }

    @GetMapping("/search/recently-updated")
    public ResponseEntity<Resources<R>> recentlyUpdated(@RequestParam(value = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(new Resources<R>(assembler.toResources(repo.findMostRecentlyUpdate(limit))));
    }

    @Override
    public Resource<D> process(Resource<D> resource) {
        // @formatter:off
        try {
            resource.add(
              ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder
                  .methodOn(this.getClass())
                  .export(resource.getContent().getId(), "docx")
              ).withRel("export")
            );
        } catch (UnknownExporterTypeException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // @formatter:on
        return resource;
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
