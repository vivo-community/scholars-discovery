package edu.tamu.scholars.middleware.discovery.service.export;

import java.util.List;

import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.export.argument.ExportArg;

public interface Exporter {

    public String type();

    public String contentDisposition();

    public String contentType();

    public <D extends AbstractSolrDocument> StreamingResponseBody streamSolrResponse(Cursor<D> cursor, List<ExportArg> export);

}
