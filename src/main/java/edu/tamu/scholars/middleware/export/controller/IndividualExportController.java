package edu.tamu.scholars.middleware.export.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;
import edu.tamu.scholars.middleware.discovery.resource.IndividualResource;
import edu.tamu.scholars.middleware.export.argument.ExportArg;
import edu.tamu.scholars.middleware.export.exception.UnknownExporterTypeException;
import edu.tamu.scholars.middleware.export.service.Exporter;
import edu.tamu.scholars.middleware.export.service.ExporterRegistry;

@RepositoryRestController
@RequestMapping("/individual")
public class IndividualExportController implements RepresentationModelProcessor<IndividualResource> {

    @Autowired
    private IndividualRepo repo;

    @Autowired
    private ExporterRegistry exporterRegistry;

    @GetMapping("/search/export")
    // @formatter:off
    public ResponseEntity<StreamingResponseBody> export(
        @RequestParam(value = "type", required = false, defaultValue = "csv") String type,
        QueryArg query,
        @SortDefault Sort sort,
        List<FilterArg> filters,
        List<BoostArg> boosts,
        List<ExportArg> export
    ) throws UnknownExporterTypeException {
        Exporter exporter = exporterRegistry.getExporter(type);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, exporter.contentDisposition("export"))
            .header(HttpHeaders.CONTENT_TYPE, exporter.contentType())
            .body(exporter.streamSolrResponse(repo.stream(query, filters, boosts, sort), export));
    }
    // @formatter:on

    @GetMapping("/{id}/export")
    // @formatter:off
    public ResponseEntity<StreamingResponseBody> export(
        @PathVariable String id,
        @RequestParam(value = "type", required = false, defaultValue = "docx") String type,
        @RequestParam(value = "name", required = true) String name
    ) throws UnknownExporterTypeException, IllegalArgumentException, IllegalAccessException {
        Optional<Individual> individual = repo.findById(id);
        if (individual.isPresent()) {
            Individual document = individual.get();
            Exporter exporter = exporterRegistry.getExporter(type);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, exporter.contentDisposition(id))
                .header(HttpHeaders.CONTENT_TYPE, exporter.contentType())
                .body(exporter.streamIndividual(document, name));
        }
        throw new EntityNotFoundException(String.format("Individual with id %s not found", id));
    }
    // @formatter:on

    @Override
    public IndividualResource process(IndividualResource resource) {
        try {
            // @formatter:off
            resource.add(
                WebMvcLinkBuilder.linkTo(
                  WebMvcLinkBuilder
                    .methodOn(this.getClass())
                    .export(resource.getContent().getId(), "docx", "Export Name")
                ).withRel("export")
            );
            // @formatter:on
        } catch (UnknownExporterTypeException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return resource;
    }

}
