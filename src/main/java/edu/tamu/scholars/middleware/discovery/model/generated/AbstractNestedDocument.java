package edu.tamu.scholars.middleware.discovery.model.generated;

import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import java.io.Serializable;
import java.lang.String;

public abstract class AbstractNestedDocument extends AbstractSolrDocument implements Serializable {
  private static final long serialVersionUID = -6742176553037076125L;

  private String label;

  public AbstractNestedDocument() {
    super();
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
