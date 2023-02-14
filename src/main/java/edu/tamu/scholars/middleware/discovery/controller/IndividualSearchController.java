package edu.tamu.scholars.middleware.discovery.controller;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DEFAULT_QUERY;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/individual/search/findByIdIn")
    public ResponseEntity<CollectionModel<IndividualResource>> findByIdIn(@RequestParam(required = true) List<String> ids) {
        return ResponseEntity.ok(assembler.toCollectionModel(repo.findByIdIn(ids)));
    }

    @GetMapping("/individual/search/findByType")
    public ResponseEntity<CollectionModel<IndividualResource>> findByType(@RequestParam(required = true) String type) {
        return ResponseEntity.ok(assembler.toCollectionModel(repo.findByType(type)));
    }

    @GetMapping("/individual/search/recentlyUpdated")
    public ResponseEntity<CollectionModel<IndividualResource>> recentlyUpdated(
        @RequestParam(value = "limit", defaultValue = "10") int limit,
        List<FilterArg> filters
    ) {
        return ResponseEntity.ok(assembler.toCollectionModel(repo.findMostRecentlyUpdate(limit, filters)));
    }

    @GetMapping("/individual/search/advanced")
    public ResponseEntity<PagedModel<IndividualResource>> search(
        QueryArg query,
        List<FacetArg> facets,
        List<FilterArg> filters,
        List<BoostArg> boosts,
        HighlightArg highlight,
        @PageableDefault(page = 0, size = 10, sort = ID, direction = ASC) Pageable page
    ) {
        return ResponseEntity.ok(discoveryPagedResourcesAssembler.toModel(repo.search(query, facets, filters, boosts, highlight, page), assembler));
    }

    @Override
    public RepositorySearchesResource process(RepositorySearchesResource resource) {
        if (Individual.class.equals(resource.getDomainType())) {
            resource.add(linkTo(methodOn(IndividualSearchCountController.class).count(
                DEFAULT_QUERY,
                new ArrayList<FilterArg>()
            )).withRel("count").withTitle("Count query"));

            resource.add(linkTo(methodOn(this.getClass()).findByIdIn(
                new ArrayList<String>()
            )).withRel("findByIdIn").withTitle("Search by ids"));

            resource.add(linkTo(methodOn(this.getClass()).findByType(
                "Person"
            )).withRel("findByType").withTitle("Search by type"));

            resource.add(linkTo(methodOn(this.getClass()).recentlyUpdated(
                10,
                new ArrayList<FilterArg>()
            )).withRel("recentlyUpdated").withTitle("Recently updated query"));

            resource.add(linkTo(methodOn(this.getClass()).search(
                QueryArg.of(
                    Optional.of(DEFAULT_QUERY),
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
            )).withRel("advanced").withTitle("Advanced search"));
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
