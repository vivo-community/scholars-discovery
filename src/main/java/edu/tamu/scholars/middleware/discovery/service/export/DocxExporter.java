package edu.tamu.scholars.middleware.discovery.service.export;

import javax.xml.bind.JAXBException;

import org.docx4j.convert.in.xhtml.DivToSdt;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.tamu.scholars.middleware.discovery.exception.ExportException;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.service.HandlebarsService;
import edu.tamu.scholars.middleware.view.model.DisplayView;

@Service
public class DocxExporter implements Exporter {

    private static final String TYPE = "docx";

    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    private static final String CONTENT_DISPOSITION = "attachment; filename=export.docx";

    private static final String HYPERLINK_STYLE = "Hyperlink";

    @Autowired
    private HandlebarsService handlebarsService;

    @Autowired
    private ObjectMapper mapper;

    @Value("${vivo.base-url:http://localhost:8080/vivo}")
    private String vivoUrl;

    @Value("${ui.url:http://localhost:4200}")
    private String uiUrl;

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String contentDisposition() {
        return CONTENT_DISPOSITION;
    }

    @Override
    public String contentType() {
        return CONTENT_TYPE;
    }

    @Override
    public <D extends AbstractSolrDocument> StreamingResponseBody streamIndividual(D document, DisplayView view) {
        return outputStream -> {
            try {
                WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
                NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();

                wordMLPackage.getMainDocumentPart().addTargetPart(ndp);

                ndp.unmarshalDefaultNumbering();

                XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);

                XHTMLImporter.setHyperlinkStyle(HYPERLINK_STYLE);

                XHTMLImporter.setDivHandler(new DivToSdt());

                ObjectNode json = mapper.valueToTree(document);

                json.put("vivoUrl", vivoUrl);
                json.put("uiUrl", uiUrl);

                String html = handlebarsService.template(view.getExportTemplate(), json);

                MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();

                mdp.addAltChunk(AltChunkType.Xhtml, html.getBytes());

                wordMLPackage.save(outputStream);

            } catch (JAXBException | Docx4JException e) {
                throw new ExportException(e.getMessage());
            }
        };
    }

}
