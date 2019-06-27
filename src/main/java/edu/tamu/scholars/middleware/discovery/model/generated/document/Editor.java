package edu.tamu.scholars.middleware.discovery.model.generated.document;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class Editor extends AbstractNestedDocument {
  private static final long serialVersionUID = -9076487591811219968L;

  private String type;

  private String rank;

  public Editor() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }
}
