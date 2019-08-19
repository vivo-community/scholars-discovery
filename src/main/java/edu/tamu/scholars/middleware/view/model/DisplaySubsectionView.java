package edu.tamu.scholars.middleware.view.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "display_subsections")
@AttributeOverride(name = "name", column = @Column(nullable = false))
public class DisplaySubsectionView extends FieldView {

    private static final long serialVersionUID = 7776446742411477782L;

    @Column(nullable = false)
    private int pageSize;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String template;

    public DisplaySubsectionView() {
        super();
        pageSize = 5;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

}
