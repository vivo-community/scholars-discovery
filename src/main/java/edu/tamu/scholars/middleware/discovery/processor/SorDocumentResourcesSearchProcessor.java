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
        final Link facetSearchLink = new Link(search + "/faceted{?query,page,size,sort,facets,filters,boosts}").withRel("faceted");
        final Link exportSearchLink = new Link(search + "/export{?type,query,sort,filters,boosts,export}").withRel("export");
        final Link countSearchLink = new Link(search + "/count{?query,filters}").withRel("count");
        final Link recentSearchLink = new Link(search + "/recently-updated{?limit,filters}").withRel("recently-updated");
        resource.add(facetSearchLink);
        resource.add(exportSearchLink);
        resource.add(countSearchLink);
        resource.add(recentSearchLink);
        return resource;
    }

}
