package edu.tamu.scholars.middleware.graphql.service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

/* Note: this class extends AbstractPageRequest, but defers all operations to a
     contained instance of PageRequest that also extends AbstractPageRequest.
     The extension here is required because PageRequest does not support
     defaults and to ensure that the defaultable page request
     is valid in all circumstances where references to
     PageRequest (implements Pageable) are required.
     Since the ctor for AbstractPageRequest requires page and size parameters, the
     defaults are supplied, but never used.
 */
public class DefaultablePageRequest extends AbstractPageRequest {
  private PageRequest pager = PageRequest.of(0, 100);

  public DefaultablePageRequest() {
    super( 0, 100);
    this.pager = PageRequest.of(0, 100);
  }

  public DefaultablePageRequest(String anyThing) {
    super(0, 100);
    this.pager = PageRequest.of(0, 100);
  }

  public DefaultablePageRequest(int pageNumber, int pageSize, Sort sort) {
    super( 0, 100);
    this.pager = PageRequest.of(pageNumber, pageSize, sort);
  }

  public DefaultablePageRequest(int pageNumber, int pageSize) {
    super( 0, 100);
    this.pager = PageRequest.of(pageNumber, pageSize);
  }

  public DefaultablePageRequest(PageRequest pageRequest) {
    super( 0, 100);
    this.pager = pageRequest;
  }

  @Override
  public int getPageSize() {
    return this.pager.getPageSize();
  }

  @Override
  public int getPageNumber() {
    return this.pager.getPageNumber();
  }

  @Override
  public long getOffset() {
    return this.pager.getOffset();
  }

  @Override
  public boolean hasPrevious () {
    return this.pager.hasPrevious();
  }

  // previousOrFirst is not overridden since it only uses methods
  //  that already defer to the contained PageRequest instance

  @Override
  public Sort getSort() {
    return this.pager.getSort();
  }

  @Override
  public Pageable next() {
    return new DefaultablePageRequest((PageRequest)this.pager.next());
  }

  @Override
  public Pageable previous() {
    return new DefaultablePageRequest((PageRequest)this.pager.previous());
  }

  @Override
  public Pageable first() {
    return new DefaultablePageRequest((PageRequest)this.pager.first());
  }

  @Override
  public int hashCode() {
    return this.pager.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    return this.pager.equals(obj);
  }

  @Override
  public String toString() {
    return this.pager.toString();
  }
}
