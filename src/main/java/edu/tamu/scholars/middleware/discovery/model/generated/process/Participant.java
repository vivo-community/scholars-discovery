package edu.tamu.scholars.middleware.discovery.model.generated.process;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class Participant extends AbstractNestedDocument {
  private static final long serialVersionUID = 8368968245713499582L;

  private String role;

  public Participant() {
    super();
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
