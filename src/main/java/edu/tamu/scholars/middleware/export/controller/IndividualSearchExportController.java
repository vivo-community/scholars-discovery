package edu.tamu.scholars.middleware.export.controller;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DEFAULT_QUERY;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;
import edu.tamu.scholars.middleware.export.argument.ExportArg;
import edu.tamu.scholars.middleware.export.exception.UnknownExporterTypeException;
import edu.tamu.scholars.middleware.export.service.Exporter;
import edu.tamu.scholars.middleware.export.service.ExporterRegistry;

@RestController
public class IndividualSearchExportController implements RepresentationModelProcessor<RepositorySearchesResource> {

    @Autowired
    private IndividualRepo repo;

    @Autowired
    private ExporterRegistry exporterRegistry;

    @GetMapping("/individual/search/export")
    public ResponseEntity<StreamingResponseBody> export(
        @RequestParam(value = "type", required = false, defaultValue = "csv") String type,
        QueryArg query,
        @SortDefault Sort sort,
        List<FilterArg> filters,
        List<BoostArg> boosts,
        List<ExportArg> export
    ) throws UnknownExporterTypeException, InterruptedException, ExecutionException {
        Exporter exporter = exporterRegistry.getExporter(type);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, exporter.contentDisposition("export"))
            .header(HttpHeaders.CONTENT_TYPE, exporter.contentType())
            .body(exporter.streamIndividuals(repo.export(query, filters, boosts, sort), export));
    }

    @Override
    public RepositorySearchesResource process(RepositorySearchesResource resource) {
        if (Individual.class.equals(resource.getDomainType())) {
            try {
                resource.add(linkTo(methodOn(this.getClass()).export(
                    "csv",
                    QueryArg.of(
                        Optional.of(DEFAULT_QUERY),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                    ),
                    Sort.unsorted(),
                    new ArrayList<FilterArg>(),
                    new ArrayList<BoostArg>(),
                    new ArrayList<ExportArg>()
                )).withRel("export").withTitle("Discovery export"));
            } catch (UnknownExporterTypeException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
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
