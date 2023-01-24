package edu.tamu.scholars.middleware.discovery.controller;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tamu.scholars.middleware.discovery.DiscoveryConstants;
import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;
import edu.tamu.scholars.middleware.discovery.assembler.DiscoveryPagedResourcesAssembler;
import edu.tamu.scholars.middleware.discovery.assembler.IndividualResourceAssembler;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;
import edu.tamu.scholars.middleware.discovery.resource.IndividualResource;

@RepositoryRestController
public class IndividualSearchController implements RepresentationModelProcessor<RepositorySearchesResource> {

    @Autowired
    private IndividualRepo repo;

    @Autowired
    private IndividualResourceAssembler assembler;

    @Autowired
    private DiscoveryPagedResourcesAssembler<Individual> discoveryPagedResourcesAssembler;

    @GetMapping("/individual/search/advanced")
    // @formatter:off
    public ResponseEntity<PagedModel<IndividualResource>> search(
        QueryArg query,
        List<FacetArg> facets,
        List<FilterArg> filters,
        List<BoostArg> boosts,
        HighlightArg highlight,
        @PageableDefault(page = 0, size = 10, sort = "id", direction = ASC) Pageable page
    ) {
        return ResponseEntity.ok(discoveryPagedResourcesAssembler.toModel(repo.search(query, facets, filters, boosts, highlight, page), assembler));
    }
    // @formatter:on

    @GetMapping("/individual/search/recentlyUpdated")
    public ResponseEntity<CollectionModel<IndividualResource>> recentlyUpdated(@RequestParam(value = "limit", defaultValue = "10") int limit, List<FilterArg> filters) {
        return ResponseEntity.ok(assembler.toCollectionModel(repo.findMostRecentlyUpdate(limit, filters)));
    }

    @Override
    public RepositorySearchesResource process(RepositorySearchesResource resource) {
        if (Individual.class.equals(resource.getDomainType())) {
            resource.add(linkTo(methodOn(IndividualSearchController.class).search(
                QueryArg.of(
                    Optional.of(DiscoveryConstants.DEFAULT_QUERY),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
                ),
                new ArrayList<FacetArg>(),
                new ArrayList<FilterArg>(),
                new ArrayList<BoostArg>(),
                HighlightArg.of(new String[] {}, Optional.empty(), Optional.empty()),
                PageRequest.of(0, 10)
            )).withRel("advanced").withTitle("Advanced Search"));

            resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                .methodOn(IndividualCountController.class)
                .count(
                    DiscoveryConstants.DEFAULT_QUERY,
                    new ArrayList<FilterArg>()
                )
            ).withRel("count").withTitle("Count Query"));

            resource.add(linkTo(methodOn(IndividualSearchController.class).recentlyUpdated(
                10,
                new ArrayList<FilterArg>()
            )).withRel("recentlyUpdated").withTitle("Recently Updated Query"));
        }
        return resource;
    }

}
