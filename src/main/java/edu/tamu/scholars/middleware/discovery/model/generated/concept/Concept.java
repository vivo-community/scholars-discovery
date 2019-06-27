package edu.tamu.scholars.middleware.discovery.model.generated.concept;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;
import java.util.List;

public class Concept extends AbstractNestedDocument {
  private static final long serialVersionUID = -88888876514335165L;

  private List<Website> websites;

  private List<AssociatedDepartment> associatedDepartments;

  private List<ResearchAreaOf> researchAreaOf;

  private List<ResearchAreaOfOrganization> researchAreaOfOrganization;

  private List<BroaderConcept> broaderConcepts;

  private List<NarrowerConcept> narrowerConcepts;

  private List<RelatedConcept> relatedConcepts;

  private List<SameAs> sameAs;

  private String name;

  private List<String> type;

  private String image;

  private String thumbnail;

  private String modTime;

  public Concept() {
    super();
  }

  public List<Website> getWebsites() {
    return websites;
  }

  public void setWebsites(List<Website> websites) {
    this.websites = websites;
  }

  public List<AssociatedDepartment> getAssociatedDepartments() {
    return associatedDepartments;
  }

  public void setAssociatedDepartments(List<AssociatedDepartment> associatedDepartments) {
    this.associatedDepartments = associatedDepartments;
  }

  public List<ResearchAreaOf> getResearchAreaOf() {
    return researchAreaOf;
  }

  public void setResearchAreaOf(List<ResearchAreaOf> researchAreaOf) {
    this.researchAreaOf = researchAreaOf;
  }

  public List<ResearchAreaOfOrganization> getResearchAreaOfOrganization() {
    return researchAreaOfOrganization;
  }

  public void setResearchAreaOfOrganization(
      List<ResearchAreaOfOrganization> researchAreaOfOrganization) {
    this.researchAreaOfOrganization = researchAreaOfOrganization;
  }

  public List<BroaderConcept> getBroaderConcepts() {
    return broaderConcepts;
  }

  public void setBroaderConcepts(List<BroaderConcept> broaderConcepts) {
    this.broaderConcepts = broaderConcepts;
  }

  public List<NarrowerConcept> getNarrowerConcepts() {
    return narrowerConcepts;
  }

  public void setNarrowerConcepts(List<NarrowerConcept> narrowerConcepts) {
    this.narrowerConcepts = narrowerConcepts;
  }

  public List<RelatedConcept> getRelatedConcepts() {
    return relatedConcepts;
  }

  public void setRelatedConcepts(List<RelatedConcept> relatedConcepts) {
    this.relatedConcepts = relatedConcepts;
  }

  public List<SameAs> getSameAs() {
    return sameAs;
  }

  public void setSameAs(List<SameAs> sameAs) {
    this.sameAs = sameAs;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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

  public String getModTime() {
    return modTime;
  }

  public void setModTime(String modTime) {
    this.modTime = modTime;
  }
}
