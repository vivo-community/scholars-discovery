package edu.tamu.scholars.middleware.view.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "display_export_views")
@AttributeOverride(name = "name", column = @Column(nullable = false))
public class ExportView extends View {

    private static final long serialVersionUID = 8352195631003934922L;

    @Column(columnDefinition = "TEXT")
    private String contentTemplate;

    @Column(columnDefinition = "TEXT")
    private String headerTemplate;

    @ElementCollection
    private List<String> requiredFields;

    @ElementCollection
    private List<String> lazyReferences;

    @JoinColumn(name = "export_field_id")
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ExportFieldView> fieldViews;

    public ExportView() {
        super();
        requiredFields = new ArrayList<String>();
        lazyReferences = new ArrayList<String>();
        fieldViews = new ArrayList<ExportFieldView>();
    }

    public String getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(String contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public String getHeaderTemplate() {
        return headerTemplate;
    }

    public void setHeaderTemplate(String headerTemplate) {
        this.headerTemplate = headerTemplate;
    }

    public List<String> getRequiredFields() {
        return requiredFields;
    }

    public void setRequiredFields(List<String> requiredFields) {
        this.requiredFields = requiredFields;
    }

    public List<String> getLazyReferences() {
        return lazyReferences;
    }

    public void setLazyReferences(List<String> lazyReferences) {
        this.lazyReferences = lazyReferences;
    }

    public List<ExportFieldView> getFieldViews() {
        return fieldViews;
    }

    public void setFieldViews(List<ExportFieldView> fieldViews) {
        this.fieldViews = fieldViews;
    }

}
