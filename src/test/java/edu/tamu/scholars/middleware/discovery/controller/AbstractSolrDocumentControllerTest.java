package edu.tamu.scholars.middleware.discovery.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import edu.tamu.scholars.middleware.discovery.AbstractSolrDocumentIntegrationTest;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.utility.ConstraintDescriptionsHelper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public abstract class AbstractSolrDocumentControllerTest<D extends AbstractIndexDocument> extends AbstractSolrDocumentIntegrationTest<D> {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetSolrDocumentsPage() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/individual")
            .param("page", "0")
            .param("size", "10")
            .param("sort", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HAL_JSON_VALUE))
                .andExpect(jsonPath("page.size", equalTo(10)))
                .andExpect(jsonPath("page.totalElements", equalTo(numberOfDocuments)))
                .andExpect(jsonPath("page.totalPages", equalTo(3)))
                .andExpect(jsonPath("page.number", equalTo(1)))
                .andDo(
                    document(
                        getDocPath() + "/page",
                        requestParameters(
                            parameterWithName("page").description("The page number."),
                            parameterWithName("size").description("The page size."),
                            parameterWithName("sort").description("The page sort [field,asc/desc].")
                        ),
                        links(
                            linkWithRel("first").description("First page link for this resource."),
                            linkWithRel("self").description("Canonical link for this resource."),
                            linkWithRel("next").description("Next page link for this resource."),
                            linkWithRel("last").description("Last page link for this resource."),
                            linkWithRel("profile").description("The ALPS profile for this resource.")
                        ),
                        responseFields(
                            subsectionWithPath("_embedded.individual").description(String.format("An array of <<resources-%s, %s resources>>.", "individual", getType().getSimpleName())),
                            subsectionWithPath("_links").description(String.format("<<resources-%s-list-links, Links>> to other resources.", "individual")),
                            subsectionWithPath("page").description(String.format("Page details for <<resources-%s, %s resources>>.", "individual", getType().getSimpleName()))
                        )
                    )
                );
        // @formatter:on
    }

    @Test
    public void testGetSolrDocument() throws Exception {
        for (D mockDocument : mockDocuments) {
            ConstraintDescriptionsHelper describeDocument = new ConstraintDescriptionsHelper(mockDocument.getClass());

            // @formatter:off
            mockMvc.perform(get("/individual/{id}", mockDocument.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HAL_JSON_VALUE))
                .andDo(
                    document(
                        getDocPath() + "/findById",
                        pathParameters(
                            describeDocument.withParameter("id", String.format("The %s id.", getType().getSimpleName()))
                        )
                    )
                );
            // @formatter:on
        }
    }

    @Test
    public void testSearchSolrDocumentsFacetPage() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/individual/search/advanced")
            .param("q", "*:*")
            .param("df", "_text_")
            .param("facets", "type")
            .param("type.limit", "5")
            .param("type.offset", "0")
            .param("type.sort", "COUNT")
            .param("filters", "class")
            .param("class.filter", getType().getSimpleName())
            .param("page", "0")
            .param("size", "20")
            .param("sort", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HAL_JSON_VALUE))
                .andExpect(jsonPath("page.size", equalTo(20)))
                .andExpect(jsonPath("page.totalElements", equalTo(mockDocuments.size())))
                .andExpect(jsonPath("page.totalPages", equalTo(1)))
                .andExpect(jsonPath("page.number", equalTo(1)))
                .andDo(
                    document(
                        getDocPath() + "/search/advanced",
                        requestParameters(
                            parameterWithName("q").description("The search query."),
                            parameterWithName("df").description("The default search field."),
                            parameterWithName("facets").description("The facet fields."),
                            parameterWithName("type.limit").description("Type facet limit."),
                            parameterWithName("type.offset").description("Type facet offset."),
                            parameterWithName("type.sort").description("Type facet sort {index/count}."),
                            parameterWithName("filters").description("The filter fields."),
                            parameterWithName("class.filter").description("Class filter value."),
                            parameterWithName("page").description("The page number."),
                            parameterWithName("size").description("The page size."),
                            parameterWithName("sort").description("The page sort 'field,{asc/desc}'.")
                        ),
                        links(
                            linkWithRel("self").description("Canonical link for this resource.")
                        ),
                        responseFields(
                            subsectionWithPath("_embedded.individual").description(String.format("An array of <<resources-%s, %s resources>>.", "individual", getType().getSimpleName())),
                            subsectionWithPath("_links").description(String.format("<<resources-%s-list-links, Links>> to other resources.", "individual")),
                            subsectionWithPath("page").description(String.format("Page details for <<resources-%s, %s resources>>.", "individual", getType().getSimpleName())),
                            subsectionWithPath("facets").description(String.format("Facets for <<resources-%s, %s resources>>.", "individual", getType().getSimpleName())),
                            subsectionWithPath("highlights").description(String.format("Highlights for <<resources-%s, %s resources>>.", "individual", getType().getSimpleName()))
                        )
                    )
                );
        // @formatter:on
    }

    @Test
    public void testSearchSolrDocumentsCount() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/individual/search/count")
            .param("query", "*:*")
            .param("filters", "class")
            .param("class.filter", getType().getSimpleName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("value", equalTo(mockDocuments.size())))
                .andDo(
                    document(
                        getDocPath() + "/search/count",
                        requestParameters(
                            parameterWithName("query").description("The search query."),
                            parameterWithName("filters").description("The filter fields."),
                            parameterWithName("class.filter").description("Class filter value.")
                        ),
                        responseFields(
                            subsectionWithPath("value").description("The resulting count.")
                        )
                    )
                );
        // @formatter:on
    }

    @Test
    public void testRecentlyUpdated() throws Exception {
        // @formatter:off
        mockMvc.perform(get("/individual/search/recentlyUpdated")
            .param("limit", "2")
            .param("filters", "class")
            .param("class.filter", getType().getSimpleName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HAL_JSON_VALUE))
                .andExpect(jsonPath(String.format("$._embedded.individual.length()"), equalTo(2)))
                .andDo(
                    document(
                        getDocPath() + "/search/recentlyUpdated",
                        requestParameters(
                            parameterWithName("limit").description("The number of recently updated documents to return."),
                            parameterWithName("filters").description("The filter fields."),
                            parameterWithName("class.filter").description("Class filter value.")
                        ),
                        responseFields(
                            subsectionWithPath("_embedded.individual").description(String.format("An array of <<resources-%s, %s resources>>.", "individual", getType().getSimpleName()))
                        )
                    )
                );
        // @formatter:on
    }

    @Test
    public void testFindByIdIn() throws Exception {
        List<String> ids = new ArrayList<String>();
        for (D mockDocument : mockDocuments) {
            ids.add(mockDocument.getId());
        }
        ConstraintDescriptionsHelper describeDocument = new ConstraintDescriptionsHelper(getType());
        // @formatter:off
        mockMvc.perform(get("/individual/search/findByIdIn")
            .param("ids", String.join(",", ids)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(HAL_JSON_VALUE))
                .andDo(
                    document(
                        getDocPath() + "/search/findByIdIn",
                        requestParameters(
                            describeDocument.withParameter("ids", String.format("The %s ids.", getType().getSimpleName()))
                        ),
                        responseFields(
                            subsectionWithPath("_embedded.individual").description(String.format("An array of <<resources-%s, %s resources>>.", "indiviudal", getType().getSimpleName()))
                        )
                    )
                );
        // @formatter:on
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testPost() throws Exception {
        mockMvc.perform(post("/individual").content("{}")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testPut() throws Exception {
        mockMvc.perform(put("/individual/" + mockDocuments.get(0).getId()).content("{}")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testPatch() throws Exception {
        mockMvc.perform(put("/individual/" + mockDocuments.get(0).getId()).content("{}")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/individual/" + mockDocuments.get(0).getId())).andExpect(status().isMethodNotAllowed());
    }

}
