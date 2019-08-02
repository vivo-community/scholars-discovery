package edu.tamu.scholars.middleware.export.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.discovery.argument.Filter;
import edu.tamu.scholars.middleware.discovery.argument.Index;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;
import edu.tamu.scholars.middleware.export.argument.Export;
import edu.tamu.scholars.middleware.export.exception.UnknownExporterTypeException;
import edu.tamu.scholars.middleware.export.service.Exporter;
import edu.tamu.scholars.middleware.export.service.ExporterRegistry;

public abstract class AbstractSolrDocumentExportController<D extends AbstractSolrDocument, SDR extends SolrDocumentRepo<D>> implements ResourceProcessor<Resource<D>> {

    @Autowired
    private SDR repo;

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

}
