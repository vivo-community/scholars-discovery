package edu.tamu.scholars.middleware.discovery.controller;

import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.assembler.FacetPagedResourcesAssembler;
import edu.tamu.scholars.middleware.discovery.assembler.IndividualResourceAssembler;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndividualRepo;
import edu.tamu.scholars.middleware.discovery.resource.IndividualResource;

@RepositoryRestController
@RequestMapping("/individual")
public class IndividualController {

    @Autowired
    private IndividualRepo repo;

    @Autowired
    private IndividualResourceAssembler assembler;

    @Autowired
    private FacetPagedResourcesAssembler<Individual> pagedResourcesAssembler;

    // TODO: combine query and df into simple object and add argument resolver
    @GetMapping("/search/faceted")
    // @formatter:off
    public ResponseEntity<PagedModel<IndividualResource>> search(
        @RequestParam(value = "query", required = false, defaultValue = "*:*") String query,
        @RequestParam(value = "df", required = false, defaultValue = "") String df,
        @PageableDefault(page = 0, size = 10, sort = "id", direction = ASC) Pageable page,
        List<FacetArg> facets,
        List<FilterArg> filters,
        List<BoostArg> boosts
    ) {
    // @formatter:on
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(repo.search(query, df, facets, filters, boosts, page), assembler));
    }

    @GetMapping("/search/count")
    public ResponseEntity<Count> count(@RequestParam(value = "query", required = false, defaultValue = "*:*") String query, List<FilterArg> filters) {
        return ResponseEntity.ok(new Count(repo.count(query, filters)));
    }

    @GetMapping("/search/recently-updated")
    public ResponseEntity<CollectionModel<IndividualResource>> recentlyUpdated(@RequestParam(value = "limit", defaultValue = "10") int limit, List<FilterArg> filters) {
        return ResponseEntity.ok(assembler.toCollectionModel(repo.findMostRecentlyUpdate(limit, filters)));
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
