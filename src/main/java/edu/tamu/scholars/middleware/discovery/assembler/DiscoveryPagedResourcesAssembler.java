package edu.tamu.scholars.middleware.discovery.assembler;

import static edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetAndHighlightPage.buildHighlights;
import static edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage.buildFacets;
import static edu.tamu.scholars.middleware.discovery.utility.ArgumentUtility.getFacetArguments;
import static edu.tamu.scholars.middleware.discovery.utility.ArgumentUtility.getHightlightArgument;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetAndHighlightPage.Highlight;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage.Facet;

@Service
@Scope(value = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
public class DiscoveryPagedResourcesAssembler<T> extends PagedResourcesAssembler<T> {

    @Autowired
    private HttpServletRequest request;

    public DiscoveryPagedResourcesAssembler(@Nullable HateoasPageableHandlerMethodArgumentResolver resolver, @Nullable UriComponents baseUri) {
        super(resolver, baseUri);
    }

    @Override
    protected <R extends RepresentationModel<?>, S> PagedModel<R> createPagedModel(List<R> resources, PagedModel.PageMetadata metadata, Page<S> page) {
        PagedModel<R> pagedResource = super.createPagedModel(resources, metadata, page);
        if (page instanceof FacetAndHighlightPage) {
            return new FacetAndHightlightPagedResource<R, S>(pagedResource, (FacetAndHighlightPage<S>) page, request);
        }
        if (page instanceof FacetPage) {
            return new FacetPagedResource<R, S>(pagedResource, (FacetPage<S>) page, request);
        }
        return pagedResource;
    }

    class FacetPagedResource<R extends RepresentationModel<?>, S> extends PagedModel<R> {

        private final List<Facet> facets;

        FacetPagedResource(PagedModel<R> pagedResources, FacetPage<S> facetPage, HttpServletRequest request) {
            super(pagedResources.getContent(), pagedResources.getMetadata(), pagedResources.getLinks());
            this.facets = buildFacets(facetPage, getFacetArguments(request));
        }

        public List<Facet> getFacets() {
            return facets;
        }

    }

    class FacetAndHightlightPagedResource<R extends RepresentationModel<?>, S> extends PagedModel<R> {

        private final List<Facet> facets;

        private final List<Highlight> highlights;

        @SuppressWarnings("unchecked")
        FacetAndHightlightPagedResource(PagedModel<R> pagedResources, FacetAndHighlightPage<S> facetAndHighlightPage, HttpServletRequest request) {
            super(pagedResources.getContent(), pagedResources.getMetadata(), pagedResources.getLinks());
            this.facets = buildFacets((FacetPage<S>) facetAndHighlightPage, getFacetArguments(request));
            this.highlights = buildHighlights(facetAndHighlightPage, getHightlightArgument(request));
        }

        public List<Facet> getFacets() {
            return facets;
        }

        public List<Highlight> getHighlights() {
            return highlights;
        }

    }

}