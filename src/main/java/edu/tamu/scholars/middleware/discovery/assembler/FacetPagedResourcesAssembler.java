package edu.tamu.scholars.middleware.discovery.assembler;

import static edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage.buildFacets;
import static edu.tamu.scholars.middleware.discovery.utility.ArgumentUtility.getFacetArguments;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage.Facet;

@Service
@Scope(value = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
public class FacetPagedResourcesAssembler<T> extends PagedResourcesAssembler<T> {

    @Autowired
    private HttpServletRequest request;

    public FacetPagedResourcesAssembler(@Nullable HateoasPageableHandlerMethodArgumentResolver resolver, @Nullable UriComponents baseUri) {
        super(resolver, baseUri);
    }

    @Override
    protected <R extends ResourceSupport, S> PagedResources<R> createPagedResource(List<R> resources, PagedResources.PageMetadata metadata, Page<S> page) {
        PagedResources<R> pagedResource = super.createPagedResource(resources, metadata, page);
        if (page instanceof FacetPage) {
            return new FacetPagedResource<R, S>(pagedResource, (FacetPage<S>) page, request);
        }
        return pagedResource;
    }

    class FacetPagedResource<R extends ResourceSupport, S> extends PagedResources<R> {

        private final List<Facet> facets;

        FacetPagedResource(PagedResources<R> pagedResources, FacetPage<S> facetPage, HttpServletRequest request) {
            super(pagedResources.getContent(), pagedResources.getMetadata(), pagedResources.getLinks());
            this.facets = buildFacets(facetPage, getFacetArguments(request));
        }

        public List<Facet> getFacets() {
            return facets;
        }

    }

}