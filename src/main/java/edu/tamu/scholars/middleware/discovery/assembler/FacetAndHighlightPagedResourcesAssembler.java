package edu.tamu.scholars.middleware.discovery.assembler;

import static edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetAndHighlightPage.buildHighlights;
import static edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage.buildFacets;
import static edu.tamu.scholars.middleware.discovery.utility.ArgumentUtility.getFacetArguments;
import static edu.tamu.scholars.middleware.discovery.utility.ArgumentUtility.getHightlightArgument;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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

import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetPage.Facet;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryPage;
import io.leangen.graphql.annotations.types.GraphQLType;

@Primary
@Service
@Scope(value = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
public class FacetAndHighlightPagedResourcesAssembler<T> extends PagedResourcesAssembler<T> {

    @Autowired
    private HttpServletRequest request;

    public FacetAndHighlightPagedResourcesAssembler(@Nullable HateoasPageableHandlerMethodArgumentResolver resolver, @Nullable UriComponents baseUri) {
        super(resolver, baseUri);
    }

    @Override
    protected <R extends RepresentationModel<?>, S> PagedModel<R> createPagedModel(List<R> resources, PagedModel.PageMetadata metadata, Page<S> page) {
        PagedModel<R> pagedResource = super.createPagedModel(resources, metadata, page);
        if (page instanceof FacetAndHighlightPage) {
            return new FacetAndHightlightPagedResource<R, S>(pagedResource, (FacetAndHighlightPage<S>) page, request);
        }
        return pagedResource;
    }

    class FacetAndHightlightPagedResource<R extends RepresentationModel<?>, S> extends PagedModel<R> {

        private final List<Facet> facets;

        private final List<Highlight> highlights;

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

    @GraphQLType(name = "Highlight")
    public static class Highlight {

        private final String id;

        private final Map<String, List<String>> snippets;

        public Highlight(String id, Map<String, List<String>> snippets) {
            this.id = id;
            this.snippets = snippets;
        }

        public String getId() {
            return id;
        }

        public Map<String, List<String>> getSnippets() {
            return snippets;
        }

    }

}