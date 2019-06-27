package edu.tamu.scholars.middleware.discovery.model.generated.process;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class Website extends AbstractNestedDocument {
  private static final long serialVersionUID = 3150250594795528957L;

  private String url;

  public Website() {
    super();
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
