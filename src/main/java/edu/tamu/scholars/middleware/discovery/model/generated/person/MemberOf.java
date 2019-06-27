package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class MemberOf extends AbstractNestedDocument {
  private static final long serialVersionUID = -2015783668L;

  private String type;

  private MemberOfOrganization organization;

  private String startDate;

  private String endDate;

  public MemberOf() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public MemberOfOrganization getOrganization() {
    return organization;
  }

  public void setOrganization(MemberOfOrganization organization) {
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
