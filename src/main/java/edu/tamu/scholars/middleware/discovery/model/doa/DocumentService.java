package edu.tamu.scholars.middleware.discovery.model.doa;

import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.Document;
import edu.tamu.scholars.middleware.discovery.model.repo.DocumentRepo;

@Service
public class DocumentService extends AbstractSolrDocumentService<edu.tamu.scholars.middleware.discovery.model.generated.Document, Document, DocumentRepo> {

    @Override
    protected Class<?> getNestedDocumentClass() {
        return edu.tamu.scholars.middleware.discovery.model.generated.Document.class;
    }

}
