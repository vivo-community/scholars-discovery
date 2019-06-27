package edu.tamu.scholars.middleware.discovery.model.generated.relationship;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;
import java.util.List;

public class Relationship extends AbstractNestedDocument {
  private static final long serialVersionUID = -1736877008L;

  private List<ReceiptOf> receiptOf;

  private List<AwardOrHonorFor> awardOrHonorFor;

  private List<AwardConferredBy> awardConferredBy;

  private List<AwardedBy> awardedBy;

  private List<GrantSubcontractedThrough> grantSubcontractedThrough;

  private List<AdministeredBy> administeredBy;

  private List<GeographicFocus> geographicFocus;

  private List<SubGrant> subGrant;

  private List<SubGrantOf> subGrantOf;

  private List<ProvidesFundingFor> providesFundingFor;

  private List<Contributor> contributors;

  private List<PrincipalInvestigator> principalInvestigators;

  private List<CoPrincipalInvestigator> coPrincipalInvestigators;

  private List<SupportedPublicationOrOtherWork> supportedPublicationOrOtherWork;

  private List<SubjectArea> subjectAreas;

  private List<SameAs> sameAs;

  private List<InheresIn> inheresIn;

  private List<SpecifiedOutputOf> specifiedOutputOf;

  private List<OutputOf> outputOf;

  private List<ParticipatesIn> participatesIn;

  private String title;

  private List<String> type;

  private String image;

  private String thumbnail;

  @JsonProperty("abstract")
  private String abstractText;

  private String description;

  private List<String> awardConferredByPreferredLabel;

  private List<String> awardedByPreferredLabel;

  private String totalAwardAmount;

  private String directCosts;

  private String sponsorAwardId;

  private String localAwardId;

  private String dateTimeIntervalStart;

  private String dateTimeIntervalEnd;

  private String yearAwarded;

  private String modTime;

  public Relationship() {
    super();
  }

  public List<ReceiptOf> getReceiptOf() {
    return receiptOf;
  }

  public void setReceiptOf(List<ReceiptOf> receiptOf) {
    this.receiptOf = receiptOf;
  }

  public List<AwardOrHonorFor> getAwardOrHonorFor() {
    return awardOrHonorFor;
  }

  public void setAwardOrHonorFor(List<AwardOrHonorFor> awardOrHonorFor) {
    this.awardOrHonorFor = awardOrHonorFor;
  }

  public List<AwardConferredBy> getAwardConferredBy() {
    return awardConferredBy;
  }

  public void setAwardConferredBy(List<AwardConferredBy> awardConferredBy) {
    this.awardConferredBy = awardConferredBy;
  }

  public List<AwardedBy> getAwardedBy() {
    return awardedBy;
  }

  public void setAwardedBy(List<AwardedBy> awardedBy) {
    this.awardedBy = awardedBy;
  }

  public List<GrantSubcontractedThrough> getGrantSubcontractedThrough() {
    return grantSubcontractedThrough;
  }

  public void setGrantSubcontractedThrough(
      List<GrantSubcontractedThrough> grantSubcontractedThrough) {
    this.grantSubcontractedThrough = grantSubcontractedThrough;
  }

  public List<AdministeredBy> getAdministeredBy() {
    return administeredBy;
  }

  public void setAdministeredBy(List<AdministeredBy> administeredBy) {
    this.administeredBy = administeredBy;
  }

  public List<GeographicFocus> getGeographicFocus() {
    return geographicFocus;
  }

  public void setGeographicFocus(List<GeographicFocus> geographicFocus) {
    this.geographicFocus = geographicFocus;
  }

  public List<SubGrant> getSubGrant() {
    return subGrant;
  }

  public void setSubGrant(List<SubGrant> subGrant) {
    this.subGrant = subGrant;
  }

  public List<SubGrantOf> getSubGrantOf() {
    return subGrantOf;
  }

  public void setSubGrantOf(List<SubGrantOf> subGrantOf) {
    this.subGrantOf = subGrantOf;
  }

  public List<ProvidesFundingFor> getProvidesFundingFor() {
    return providesFundingFor;
  }

  public void setProvidesFundingFor(List<ProvidesFundingFor> providesFundingFor) {
    this.providesFundingFor = providesFundingFor;
  }

  public List<Contributor> getContributors() {
    return contributors;
  }

  public void setContributors(List<Contributor> contributors) {
    this.contributors = contributors;
  }

  public List<PrincipalInvestigator> getPrincipalInvestigators() {
    return principalInvestigators;
  }

  public void setPrincipalInvestigators(List<PrincipalInvestigator> principalInvestigators) {
    this.principalInvestigators = principalInvestigators;
  }

  public List<CoPrincipalInvestigator> getCoPrincipalInvestigators() {
    return coPrincipalInvestigators;
  }

  public void setCoPrincipalInvestigators(List<CoPrincipalInvestigator> coPrincipalInvestigators) {
    this.coPrincipalInvestigators = coPrincipalInvestigators;
  }

  public List<SupportedPublicationOrOtherWork> getSupportedPublicationOrOtherWork() {
    return supportedPublicationOrOtherWork;
  }

  public void setSupportedPublicationOrOtherWork(
      List<SupportedPublicationOrOtherWork> supportedPublicationOrOtherWork) {
    this.supportedPublicationOrOtherWork = supportedPublicationOrOtherWork;
  }

  public List<SubjectArea> getSubjectAreas() {
    return subjectAreas;
  }

  public void setSubjectAreas(List<SubjectArea> subjectAreas) {
    this.subjectAreas = subjectAreas;
  }

  public List<SameAs> getSameAs() {
    return sameAs;
  }

  public void setSameAs(List<SameAs> sameAs) {
    this.sameAs = sameAs;
  }

  public List<InheresIn> getInheresIn() {
    return inheresIn;
  }

  public void setInheresIn(List<InheresIn> inheresIn) {
    this.inheresIn = inheresIn;
  }

  public List<SpecifiedOutputOf> getSpecifiedOutputOf() {
    return specifiedOutputOf;
  }

  public void setSpecifiedOutputOf(List<SpecifiedOutputOf> specifiedOutputOf) {
    this.specifiedOutputOf = specifiedOutputOf;
  }

  public List<OutputOf> getOutputOf() {
    return outputOf;
  }

  public void setOutputOf(List<OutputOf> outputOf) {
    this.outputOf = outputOf;
  }

  public List<ParticipatesIn> getParticipatesIn() {
    return participatesIn;
  }

  public void setParticipatesIn(List<ParticipatesIn> participatesIn) {
    this.participatesIn = participatesIn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public String getAbstractText() {
    return abstractText;
  }

  public void setAbstractText(String abstractText) {
    this.abstractText = abstractText;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getAwardConferredByPreferredLabel() {
    return awardConferredByPreferredLabel;
  }

  public void setAwardConferredByPreferredLabel(List<String> awardConferredByPreferredLabel) {
    this.awardConferredByPreferredLabel = awardConferredByPreferredLabel;
  }

  public List<String> getAwardedByPreferredLabel() {
    return awardedByPreferredLabel;
  }

  public void setAwardedByPreferredLabel(List<String> awardedByPreferredLabel) {
    this.awardedByPreferredLabel = awardedByPreferredLabel;
  }

  public String getTotalAwardAmount() {
    return totalAwardAmount;
  }

  public void setTotalAwardAmount(String totalAwardAmount) {
    this.totalAwardAmount = totalAwardAmount;
  }

  public String getDirectCosts() {
    return directCosts;
  }

  public void setDirectCosts(String directCosts) {
    this.directCosts = directCosts;
  }

  public String getSponsorAwardId() {
    return sponsorAwardId;
  }

  public void setSponsorAwardId(String sponsorAwardId) {
    this.sponsorAwardId = sponsorAwardId;
  }

  public String getLocalAwardId() {
    return localAwardId;
  }

  public void setLocalAwardId(String localAwardId) {
    this.localAwardId = localAwardId;
  }

  public String getDateTimeIntervalStart() {
    return dateTimeIntervalStart;
  }

  public void setDateTimeIntervalStart(String dateTimeIntervalStart) {
    this.dateTimeIntervalStart = dateTimeIntervalStart;
  }

  public String getDateTimeIntervalEnd() {
    return dateTimeIntervalEnd;
  }

  public void setDateTimeIntervalEnd(String dateTimeIntervalEnd) {
    this.dateTimeIntervalEnd = dateTimeIntervalEnd;
  }

  public String getYearAwarded() {
    return yearAwarded;
  }

  public void setYearAwarded(String yearAwarded) {
    this.yearAwarded = yearAwarded;
  }

  public String getModTime() {
    return modTime;
  }

  public void setModTime(String modTime) {
    this.modTime = modTime;
  }
}
