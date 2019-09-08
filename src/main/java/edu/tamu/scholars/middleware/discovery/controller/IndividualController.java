package edu.tamu.scholars.middleware.discovery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
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

    @GetMapping("/search/faceted")
    // @formatter:off
    public ResponseEntity<PagedResources<IndividualResource>> search(
        @RequestParam(value = "query", required = false, defaultValue = "*:*") String query,
        @PageableDefault Pageable pageable,
        List<FacetArg> facets,
        List<FilterArg> filters,
        List<BoostArg> boosts
    ) {
    // @formatter:on
        FacetPage<Individual> page = repo.search(query, facets, filters, boosts, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toResource(page, assembler));
    }

    @GetMapping("/search/count")
    public ResponseEntity<Count> count(@RequestParam(value = "query", required = false, defaultValue = "*:*") String query, List<FilterArg> filters) {
        return ResponseEntity.ok(new Count(repo.count(query, filters)));
    }

    @GetMapping("/search/recently-updated")
    public ResponseEntity<Resources<IndividualResource>> recentlyUpdated(@RequestParam(value = "limit", defaultValue = "10") int limit, List<FilterArg> filters) {
        return ResponseEntity.ok(new Resources<IndividualResource>(assembler.toResources(repo.findMostRecentlyUpdate(limit, filters))));
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
