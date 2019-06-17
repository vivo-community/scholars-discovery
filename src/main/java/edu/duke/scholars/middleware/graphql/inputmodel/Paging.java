package edu.duke.scholars.middleware.graphql.inputmodel;

public class Paging {

  private Integer page;
  private Integer pageSize;

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getPage() {
    return this.page;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }
}
