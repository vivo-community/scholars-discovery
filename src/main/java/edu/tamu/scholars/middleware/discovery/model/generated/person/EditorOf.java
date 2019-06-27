package edu.tamu.scholars.middleware.discovery.model.generated.person;

import edu.tamu.scholars.middleware.discovery.model.generated.AbstractNestedDocument;
import java.lang.String;

public class EditorOf extends AbstractNestedDocument {
  private static final long serialVersionUID = 313957220127103529L;

  private String type;

  private String publisher;

  private String authors;

  private String pageStart;

  private String pageEnd;

  private String date;

  public EditorOf() {
    super();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getAuthors() {
    return authors;
  }

  public void setAuthors(String authors) {
    this.authors = authors;
  }

  public String getPageStart() {
    return pageStart;
  }

  public void setPageStart(String pageStart) {
    this.pageStart = pageStart;
  }

  public String getPageEnd() {
    return pageEnd;
  }

  public void setPageEnd(String pageEnd) {
    this.pageEnd = pageEnd;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
