package edu.tamu.scholars.middleware.export.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.discovery.assembler.model.IndividualModel;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;
import edu.tamu.scholars.middleware.export.exception.UnknownExporterTypeException;
import edu.tamu.scholars.middleware.export.service.Exporter;
import edu.tamu.scholars.middleware.export.service.ExporterRegistry;

@RestController
public class IndividualExportController implements RepresentationModelProcessor<IndividualModel> {

    @Autowired
    private IndividualRepo repo;

    @Autowired
    private ExporterRegistry exporterRegistry;

    @GetMapping("/individual/{id}/export")
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

    @Override
    public IndividualModel process(IndividualModel resource) {
        try {
            resource.add(linkTo(methodOn(this.getClass()).export(
                resource.getContent().getId(),
                "docx",
                "Profile Summary"
            )).withRel("export").withTitle("Individual export"));
        } catch (UnknownExporterTypeException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return resource;
    }

}
