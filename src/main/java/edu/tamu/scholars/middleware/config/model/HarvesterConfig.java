package edu.tamu.scholars.middleware.config.model;

import edu.tamu.scholars.middleware.discovery.service.Harvester;
import edu.tamu.scholars.middleware.discovery.service.jena.LocalTriplestoreHarvester;

public class HarvesterConfig extends IndexDocumentTypesConfig {
    
    private Class<? extends Harvester> type = LocalTriplestoreHarvester.class;

    public HarvesterConfig() {
        super();
    }
    
    public Class<? extends Harvester> getType() {
        return type;
    }

    public void setType(Class<? extends Harvester> type) {
        this.type = type;
    }

}
