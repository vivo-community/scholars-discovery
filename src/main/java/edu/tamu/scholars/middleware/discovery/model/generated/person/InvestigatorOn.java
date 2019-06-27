package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class InvestigatorOn extends AbstractNestedDocument {
  private static final long serialVersionUID = 2106248581784246332L;

  private String awardedBy;

  private String startDate;

  private String endDate;

  public InvestigatorOn() {
    super();
  }

  public String getAwardedBy() {
    return awardedBy;
  }

  public void setAwardedBy(String awardedBy) {
    this.awardedBy = awardedBy;
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
