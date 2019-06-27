package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class PrincipalInvestigatorOn extends AbstractNestedDocument {
  private static final long serialVersionUID = -1453996223557435538L;

  private String awardedBy;

  private String startDate;

  private String endDate;

  public PrincipalInvestigatorOn() {
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
