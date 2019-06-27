package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class TeachingActivity extends AbstractNestedDocument {
  private static final long serialVersionUID = -1893823113L;

  private String role;

  public TeachingActivity() {
    super();
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
