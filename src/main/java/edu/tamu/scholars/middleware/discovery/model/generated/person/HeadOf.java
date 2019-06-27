package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class HeadOf extends AbstractNestedDocument {
  private static final long serialVersionUID = -252862062L;

  private String type;

  private HeadOfOrganization organization;

  private String startDate;

  private String endDate;

  public HeadOf() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public HeadOfOrganization getOrganization() {
    return organization;
  }

  public void setOrganization(HeadOfOrganization organization) {
    this.organization = organization;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
}
