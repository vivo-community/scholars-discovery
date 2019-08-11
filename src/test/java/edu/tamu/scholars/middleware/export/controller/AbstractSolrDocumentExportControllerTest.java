package edu.tamu.scholars.middleware.export.controller;

import static edu.tamu.scholars.middleware.view.ViewTestUtility.getMockDisplayView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import edu.tamu.scholars.middleware.discovery.AbstractSolrDocumentIntegrationTest;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.SolrDocumentRepo;
import edu.tamu.scholars.middleware.view.model.DisplayView;
import edu.tamu.scholars.middleware.view.model.ExportFieldView;
import edu.tamu.scholars.middleware.view.model.LazyReference;
import edu.tamu.scholars.middleware.view.model.repo.DisplayViewRepo;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(SpringExtension.class)
public abstract class AbstractSolrDocumentExportControllerTest<D extends AbstractSolrDocument, R extends SolrDocumentRepo<D>> extends AbstractSolrDocumentIntegrationTest<D, R> {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisplayViewRepo displayViewRepo;

    @Test
    public void testSearchSolrDocumentsExport() throws Exception {
        // @formatter:off
        MvcResult result = mockMvc.perform(get(getPath() + "/search/export")
            .param("query", "*")
            .param("type", "csv")
            .param("export", "id,Id")
            .param("export", "type,Type")
            .param("export", "individual,Individual"))
                .andExpect(request().asyncStarted())
                .andReturn();
        result = mockMvc.perform(asyncDispatch(result))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/csv"))
            .andDo(
                document(
                    getPath().substring(1) + "/export-search",
                    requestParameters(
                        parameterWithName("query").description("The search query"),
                        parameterWithName("type").description("The search export format type"),
                        parameterWithName("export").description("The search export fields")
                    )
                )
            )
            .andReturn();
        // @formatter:on
        InputStream csvByteStream = new ByteArrayInputStream(result.getResponse().getContentAsByteArray());
        CSVParser csvParser = CSVFormat.DEFAULT.parse(new InputStreamReader(csvByteStream));
        List<CSVRecord> records = csvParser.getRecords();
        assertEquals(4, records.size());
        assertEquals("Id", records.get(0).get(0));
        assertEquals("Type", records.get(0).get(1));
        assertEquals("Individual", records.get(0).get(2));
    }

    @Test
    public void testSinglePageExport() throws Exception {
        // @formatter:off
        DisplayView mockDisplayView = getMockDisplayView();
        mockDisplayView.getExportView().setLazyReferences(new ArrayList<LazyReference>());
        mockDisplayView.getExportView().setFieldViews(new ArrayList<ExportFieldView>());
        Mockito.when(displayViewRepo.findByTypesIn(Mockito.<List<String>>any()))
            .thenReturn(Optional.of(mockDisplayView));
        String id = mockDocuments.get(0).getId();
        MvcResult result = mockMvc.perform(get(getPath() + "/{id}/export", id)
            .param("type", "docx"))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(result))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
            .andDo(
                document(
                    getPath().substring(1) + "/export-single-page",
                    pathParameters(
                        parameterWithName("id").description("The individual id")
                    ),
                    requestParameters(
                        parameterWithName("type").description("The single page export format type")
                    )
                )
            )
            .andReturn();
        // @formatter:on
    }

    protected abstract String getPath();

}
