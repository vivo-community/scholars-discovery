package edu.tamu.scholars.middleware.config.model;

import java.util.ArrayList;
import java.util.List;

import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.service.Harvester;
import edu.tamu.scholars.middleware.discovery.service.triplestore.LocalTriplestoreHarvester;

public class HarvesterConfig {

    private Class<? extends Harvester> type = LocalTriplestoreHarvester.class;

    private List<Class<? extends AbstractIndexDocument>> documentTypes = new ArrayList<Class<? extends AbstractIndexDocument>>();

    public HarvesterConfig() {

    }

    public Class<? extends Harvester> getType() {
        return type;
    }

    public void setType(Class<? extends Harvester> type) {
        this.type = type;
    }

    public List<Class<? extends AbstractIndexDocument>> getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(List<Class<? extends AbstractIndexDocument>> documentTypes) {
        this.documentTypes = documentTypes;
    }

}
