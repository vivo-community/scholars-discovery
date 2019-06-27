package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class AdviseeOf extends AbstractNestedDocument {
  private static final long serialVersionUID = 8032414266376474126L;

  private String type;

  private String candidacy;

  private String startDate;

  private String endDate;

  public AdviseeOf() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCandidacy() {
    return candidacy;
  }

  public void setCandidacy(String candidacy) {
    this.candidacy = candidacy;
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
