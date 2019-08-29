package edu.tamu.scholars.middleware.config.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "middleware.export")
public class ExportConfig {

    private String individualKey = "individual";

    private String individualBaseUri = "http://localhost:4200/display";

    private boolean includeCollection = true;

    public String getIndividualKey() {
        return individualKey;
    }

    public void setIndividualKey(String individualKey) {
        this.individualKey = individualKey;
    }

    public String getIndividualBaseUri() {
        return individualBaseUri;
    }

    public void setIndividualBaseUri(String individualBaseUri) {
        this.individualBaseUri = individualBaseUri;
    }

    public boolean isIncludeCollection() {
        return includeCollection;
    }

    public void setIncludeCollection(boolean includeCollection) {
        this.includeCollection = includeCollection;
    }

}
