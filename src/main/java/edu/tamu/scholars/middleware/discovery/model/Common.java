package edu.tamu.scholars.middleware.discovery.model;

import java.util.List;

import org.springframework.data.solr.core.mapping.Indexed;

import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.PropertySource;
import io.leangen.graphql.annotations.GraphQLIgnore;

@GraphQLIgnore
public class Common extends AbstractIndexDocument {

    @Indexed(type = "whole_strings")
    @PropertySource(template = "common/type", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#mostSpecificType", parse = true)
    private List<String> type;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "common/image", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/public#directDownloadUrl")
    private String image;

    @Indexed(type = "whole_string", searchable = false)
    @PropertySource(template = "common/thumbnail", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/public#directDownloadUrl")
    private String thumbnail;

    @Indexed(type = "nested_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "websiteUrl", key = "url") })
    @PropertySource(template = "common/website", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> websites;

    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "common/websiteUrl", predicate = "http://www.w3.org/2006/vcard/ns#url")
    private List<String> websiteUrl;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "common/geographicFocus", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> geographicFocus;

    @NestedObject
    @Indexed(type = "nested_strings", searchable = false)
    @PropertySource(template = "common/sameAs", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> sameAs;

    @Indexed(type = "pdate")
    @PropertySource(template = "common/modTime", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#modTime")
    private String modTime;

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getWebsites() {
        return websites;
    }

    public void setWebsites(List<String> websites) {
        this.websites = websites;
    }

    public List<String> getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(List<String> websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public List<String> getGeographicFocus() {
        return geographicFocus;
    }

    public void setGeographicFocus(List<String> geographicFocus) {
        this.geographicFocus = geographicFocus;
    }

    public List<String> getSameAs() {
        return sameAs;
    }

    public void setSameAs(List<String> sameAs) {
        this.sameAs = sameAs;
    }

    public String getModTime() {
        return modTime;
    }

    public void setModTime(String modTime) {
        this.modTime = modTime;
    }

}
