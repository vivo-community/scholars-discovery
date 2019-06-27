package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class EducationAndTraining extends AbstractNestedDocument {
  private static final long serialVersionUID = 888284548L;

  private EducationAndTrainingOrganization organization;

  private String field;

  private String abbreviation;

  private String startDate;

  private String endDate;

  public EducationAndTraining() {
    super();
  }

  public EducationAndTrainingOrganization getOrganization() {
    return organization;
  }

  public void setOrganization(EducationAndTrainingOrganization organization) {
    this.organization = organization;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
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
