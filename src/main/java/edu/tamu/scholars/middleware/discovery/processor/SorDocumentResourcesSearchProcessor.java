package edu.tamu.scholars.middleware.discovery.processor;

import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
public class SorDocumentResourcesSearchProcessor implements ResourceProcessor<RepositorySearchesResource> {

    @Override
    public RepositorySearchesResource process(RepositorySearchesResource resource) {
        final String search = resource.getId().getHref();
        final Link facetSearchLink = new Link(search + "/facet{?query,index,facets,page,size,sort}").withRel("facet");
        final Link countSearchLink = new Link(search + "/count{?query,fields}").withRel("count");
        final Link recentSearchLink = new Link(search + "/recently-updated{?limit}").withRel("recent");
        resource.add(facetSearchLink);
        resource.add(countSearchLink);
        resource.add(recentSearchLink);
        return resource;
    }

}
