package edu.tamu.scholars.middleware.discovery.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tamu.scholars.middleware.discovery.argument.DiscoveryNetworkDescriptor;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;
import edu.tamu.scholars.middleware.discovery.resource.IndividualResource;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryNetwork;

@RepositoryRestController
public class IndividualController implements RepresentationModelProcessor<IndividualResource> {

    @Autowired
    private IndividualRepo repo;

    @GetMapping("/individual/{id}/network")
    public ResponseEntity<DiscoveryNetwork> network(
        @PathVariable String id,
        @RequestParam(name = "dateField", defaultValue = "publicationDate") String dateField,
        @RequestParam(name = "dataFields", defaultValue = "authors") List<String> dataFields,
        @RequestParam(name = "typeFilter", defaultValue = "class:Document") String typeFilter
    ) {
        return ResponseEntity.ok(repo.network(DiscoveryNetworkDescriptor.of(id, dateField, dataFields, typeFilter)));
    }

    @Override
    public IndividualResource process(IndividualResource resource) {
        try {
            resource.add(linkTo(methodOn(this.getClass()).network(
                resource.getContent().getId(),
                "publicationDate",
                Arrays.asList("authors"),
                "class:Document"
            )).withRel("network").withTitle("Individual discovery network"));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return resource;
    }

}
