package edu.tamu.scholars.middleware.export.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;
import edu.tamu.scholars.middleware.discovery.resource.IndividualResource;
import edu.tamu.scholars.middleware.export.argument.ExportArg;
import edu.tamu.scholars.middleware.export.exception.UnknownExporterTypeException;
import edu.tamu.scholars.middleware.export.service.Exporter;
import edu.tamu.scholars.middleware.export.service.ExporterRegistry;

public abstract class AbstractSolrDocumentExportController implements ResourceProcessor<IndividualResource> {

    @Autowired
    private IndividualRepo repo;

    @Autowired
    private ExporterRegistry exporterRegistry;

    @GetMapping("/search/export")
    // @formatter:off
    public ResponseEntity<StreamingResponseBody> export(
        @RequestParam(value = "type", required = false, defaultValue = "csv") String type,
        @RequestParam(value = "query", required = false, defaultValue = "*:*") String query,
        @SortDefault Sort sort,
        List<FilterArg> filters,
        List<BoostArg> boosts,
        List<ExportArg> export
    ) throws UnknownExporterTypeException {
        Exporter exporter = exporterRegistry.getExporter(type);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, exporter.contentDisposition("individual"))
            .header(HttpHeaders.CONTENT_TYPE, exporter.contentType())
            .body(exporter.streamSolrResponse(repo.stream(query, filters, boosts, sort), export));
    }
    // @formatter:on

    @GetMapping("/{id}/export")
    // @formatter:off
    public ResponseEntity<StreamingResponseBody> export(
        @PathVariable String id,
        @RequestParam(value = "type", required = false, defaultValue = "docx") String type
    ) throws UnknownExporterTypeException, IllegalArgumentException, IllegalAccessException {
        // TODO: throw not found exception if not found
        Individual document = repo.findById(id).get();
        Exporter exporter = exporterRegistry.getExporter(type);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, exporter.contentDisposition(id))
            .header(HttpHeaders.CONTENT_TYPE, exporter.contentType())
            .body(exporter.streamIndividual(document));
    }
    // @formatter:on

    @Override
    public IndividualResource process(IndividualResource resource) {
        try {
            // @formatter:off
            resource.add(
              ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder
                  .methodOn(this.getClass())
                  .export(resource.getContent().getId(), "docx")
              ).withRel("export")
            );
            // @formatter:on
        } catch (UnknownExporterTypeException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return resource;
    }

}
