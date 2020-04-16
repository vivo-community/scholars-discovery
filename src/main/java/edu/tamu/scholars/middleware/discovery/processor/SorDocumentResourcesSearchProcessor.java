package edu.tamu.scholars.middleware.discovery.processor;

import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class SorDocumentResourcesSearchProcessor implements RepresentationModelProcessor<RepositorySearchesResource> {

    @Override
    public RepositorySearchesResource process(RepositorySearchesResource resource) {
        final Link facetSearchLink = new Link("/faceted{?query,page,size,sort,facets,filters,highlight,boosts}").withRel("faceted");
        final Link exportSearchLink = new Link("/export{?type,query,sort,filters,boosts,export}").withRel("export");
        final Link countSearchLink = new Link("/count{?query,filters}").withRel("count");
        final Link recentSearchLink = new Link("/recently-updated{?limit,filters}").withRel("recently-updated");
        resource.add(facetSearchLink);
        resource.add(exportSearchLink);
        resource.add(countSearchLink);
        resource.add(recentSearchLink);
        return resource;
    }

}
