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
@Table(name = "display_sections")
@AttributeOverride(name = "name", column = @Column(nullable = false))
public class DisplaySectionView extends FieldView {

    private static final long serialVersionUID = 938457239875938467L;

    @Column(nullable = false)
    private boolean hidden;

    @Column(nullable = false)
    private boolean shared;

    @Column(nullable = false)
    private boolean paginated;

    @Column(nullable = false)
    private int pageSize;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String template;

    @ElementCollection
    private List<String> requiredFields;

    @ElementCollection
    private List<LazyReference> lazyReferences;

    @JoinColumn(name = "display_tab_section_id")
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DisplaySubsectionView> subsections;

    public DisplaySectionView() {
        super();
        hidden = false;
        shared = false;
        paginated = false;
        pageSize = 5;
        requiredFields = new ArrayList<String>();
        lazyReferences = new ArrayList<LazyReference>();
        subsections = new ArrayList<DisplaySubsectionView>();
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean isPaginated() {
        return paginated;
    }

    public void setPaginated(boolean paginated) {
        this.paginated = paginated;
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

    public List<String> getRequiredFields() {
        return requiredFields;
    }

    public void setRequiredFields(List<String> requiredFields) {
        this.requiredFields = requiredFields;
    }

    public List<LazyReference> getLazyReferences() {
        return lazyReferences;
    }

    public void setLazyReferences(List<LazyReference> lazyReferences) {
        this.lazyReferences = lazyReferences;
    }

    public List<DisplaySubsectionView> getSubsections() {
        return subsections;
    }

    public void setSubsections(List<DisplaySubsectionView> subsections) {
        this.subsections = subsections;
    }

}
