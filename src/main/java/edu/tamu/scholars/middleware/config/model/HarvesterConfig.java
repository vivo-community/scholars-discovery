package edu.tamu.scholars.middleware.config.model;

import edu.tamu.scholars.middleware.discovery.service.Harvester;
import edu.tamu.scholars.middleware.discovery.service.jena.TriplestoreHarvester;

public class HarvesterConfig extends IndexDocumentTypesConfig {
    
    private Class<? extends Harvester> type = TriplestoreHarvester.class;

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
