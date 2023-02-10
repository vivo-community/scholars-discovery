package edu.tamu.scholars.middleware.discovery.model;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

import edu.tamu.scholars.middleware.discovery.annotation.NestedObject;
import edu.tamu.scholars.middleware.discovery.annotation.NestedObject.Reference;
import edu.tamu.scholars.middleware.discovery.annotation.FieldSource;
import edu.tamu.scholars.middleware.discovery.annotation.FieldType;

public class Common extends AbstractIndexDocument {

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "common/image", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/public#directDownloadUrl", relative = true)
    private String image;

    @Field
    @FieldType(type = "whole_string", searchable = false)
    @FieldSource(template = "common/thumbnail", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/public#directDownloadUrl", relative = true)
    private String thumbnail;

    @Field
    @FieldType(type = "nested_whole_strings", searchable = false)
    @NestedObject(properties = { @Reference(value = "websiteUrl", key = "url") })
    @FieldSource(template = "common/website", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> websites;

    @Field
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "common/websiteUrl", predicate = "http://www.w3.org/2006/vcard/ns#url")
    private List<String> websiteUrl;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "common/geographicFocus", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> geographicFocus;

    @Field
    @NestedObject
    @FieldType(type = "nested_whole_strings", searchable = false)
    @FieldSource(template = "common/sameAs", predicate = "http://www.w3.org/2000/01/rdf-schema#label")
    private List<String> sameAs;

    @Field
    @FieldType(type = "pdate")
    @FieldSource(template = "common/modTime", predicate = "http://vitro.mannlib.cornell.edu/ns/vitro/0.7#modTime")
    private String modTime;

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
