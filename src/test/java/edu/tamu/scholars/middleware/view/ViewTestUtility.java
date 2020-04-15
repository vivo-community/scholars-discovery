package edu.tamu.scholars.middleware.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.query.FacetOptions;

import edu.tamu.scholars.middleware.model.OpKey;
import edu.tamu.scholars.middleware.view.model.Boost;
import edu.tamu.scholars.middleware.view.model.DirectoryView;
import edu.tamu.scholars.middleware.view.model.DiscoveryView;
import edu.tamu.scholars.middleware.view.model.DisplaySectionView;
import edu.tamu.scholars.middleware.view.model.DisplaySubsectionView;
import edu.tamu.scholars.middleware.view.model.DisplayTabView;
import edu.tamu.scholars.middleware.view.model.DisplayView;
import edu.tamu.scholars.middleware.view.model.ExportField;
import edu.tamu.scholars.middleware.view.model.ExportFieldView;
import edu.tamu.scholars.middleware.view.model.ExportView;
import edu.tamu.scholars.middleware.view.model.Facet;
import edu.tamu.scholars.middleware.view.model.FacetType;
import edu.tamu.scholars.middleware.view.model.Filter;
import edu.tamu.scholars.middleware.view.model.Index;
import edu.tamu.scholars.middleware.view.model.Layout;
import edu.tamu.scholars.middleware.view.model.Side;
import edu.tamu.scholars.middleware.view.model.Sort;

public class ViewTestUtility {

    public static String MOCK_VIEW_NAME = "People";

    public static DirectoryView getMockDirectoryView() {
        DirectoryView directoryView = new DirectoryView();

        directoryView.setName(MOCK_VIEW_NAME);
        // directoryView.setCollection("persons");
        directoryView.setLayout(Layout.LIST);

        Map<String, String> templates = new HashMap<String, String>();
        templates.put("default", "<h1>Person template from WSYWIG</h1>");

        directoryView.setTemplates(templates);

        List<String> styles = new ArrayList<String>();

        styles.add("color: maroon;");

        directoryView.setStyles(styles);

        List<Facet> facets = new ArrayList<Facet>();

        Facet facet = new Facet();

        facet.setName("Name");
        facet.setField("name");
        facet.setType(FacetType.STRING);
        facet.setSort(FacetOptions.FacetSort.COUNT);
        facet.setDirection(Direction.DESC);
        facet.setPageSize(20);
        facet.setPageNumber(1);

        facets.add(facet);

        directoryView.setFacets(facets);

        List<Filter> filters = new ArrayList<Filter>();

        Filter filter = new Filter();

        filter.setField("type");
        filter.setValue("FacultyMember");

        filters.add(filter);

        directoryView.setFilters(filters);

        List<Boost> boosts = new ArrayList<Boost>();

        Boost boost = new Boost();

        boost.setField("name");
        boost.setValue(2.0f);

        boosts.add(boost);

        directoryView.setBoosts(boosts);

        List<Sort> sorting = new ArrayList<Sort>();

        Sort sort = new Sort();
        sort.setField("name");
        sort.setDirection(Direction.ASC);

        sorting.add(sort);

        directoryView.setSort(sorting);

        Index index = new Index();

        index.setField("name");
        index.setOpKey(OpKey.ENDS_WITH);

        directoryView.setIndex(index);

        List<ExportField> exporting = new ArrayList<ExportField>();

        ExportField idExport = new ExportField();

        idExport.setColumnHeader("Id");
        idExport.setValuePath("id");

        exporting.add(idExport);

        ExportField nameExport = new ExportField();

        nameExport.setColumnHeader("Name");
        nameExport.setValuePath("name");

        exporting.add(nameExport);

        directoryView.setExport(exporting);

        return directoryView;
    }

    public static DiscoveryView getMockDiscoveryView() {
        DiscoveryView discoveryView = new DiscoveryView();

        discoveryView.setName(MOCK_VIEW_NAME);
        discoveryView.setLayout(Layout.GRID);
        discoveryView.setDefaultSearchField("overview");

        List<String> highlightFields = new ArrayList<String>();
        highlightFields.add("overview");
        discoveryView.setHighlightFields(highlightFields);
        discoveryView.setHighlightPre("<em>");
        discoveryView.setHighlightPost("</em>");

        Map<String, String> templates = new HashMap<String, String>();
        templates.put("default", "<h1>Person template from WSYWIG</h1>");

        discoveryView.setTemplates(templates);

        List<String> styles = new ArrayList<String>();

        styles.add("color: maroon;");

        discoveryView.setStyles(styles);

        List<Facet> facets = new ArrayList<Facet>();

        Facet facet = new Facet();

        facet.setName("Name");
        facet.setField("name");
        facet.setType(FacetType.STRING);
        facet.setSort(FacetOptions.FacetSort.COUNT);
        facet.setDirection(Direction.DESC);
        facet.setPageSize(20);
        facet.setPageNumber(1);

        facets.add(facet);

        discoveryView.setFacets(facets);

        List<Filter> filters = new ArrayList<Filter>();

        Filter filter = new Filter();

        filter.setField("type");
        filter.setValue("FacultyMember");

        filters.add(filter);

        discoveryView.setFilters(filters);

        List<Boost> boosts = new ArrayList<Boost>();

        Boost boost = new Boost();

        boost.setField("name");
        boost.setValue(2.0f);

        boosts.add(boost);

        discoveryView.setBoosts(boosts);

        List<Sort> sorting = new ArrayList<Sort>();

        Sort sort = new Sort();
        sort.setField("name");
        sort.setDirection(Direction.ASC);

        sorting.add(sort);

        discoveryView.setSort(sorting);

        List<ExportField> exporting = new ArrayList<ExportField>();

        ExportField idExport = new ExportField();

        idExport.setColumnHeader("Id");
        idExport.setValuePath("id");

        exporting.add(idExport);

        ExportField nameExport = new ExportField();

        nameExport.setColumnHeader("Name");
        nameExport.setValuePath("name");

        exporting.add(nameExport);

        discoveryView.setExport(exporting);

        return discoveryView;
    }

    public static DisplayView getMockDisplayView() {
        DisplayView displayView = new DisplayView();

        displayView.setName(MOCK_VIEW_NAME);
        displayView.setMainContentTemplate("<div>Main</div>");
        displayView.setMainContentTemplate("<div>Main</div>");
        displayView.setLeftScanTemplate("<div>Left Scan</div>");
        displayView.setRightScanTemplate("<div>Right Scan</div>");
        displayView.setAsideTemplate("<div>Aside</div>");
        displayView.setAsideLocation(Side.RIGHT);

        List<String> types = new ArrayList<String>();
        types.add("FacultyMember");

        displayView.setTypes(types);

        Map<String, String> metaTemplates = new HashMap<String, String>();
        metaTemplates.put("default", "Meta tag template");

        displayView.setMetaTemplates(metaTemplates);

        Map<String, String> embedTemplates = new HashMap<String, String>();
        embedTemplates.put("default", "<div>Hello, Embedded!</div>");

        displayView.setEmbedTemplates(embedTemplates);

        List<DisplayTabView> tabs = new ArrayList<DisplayTabView>();

        tabs.add(getMockDisplayTabView());

        displayView.setTabs(tabs);

        displayView.setExportView(getMockExportView());

        return displayView;
    }

    public static DisplayTabView getMockDisplayTabView() {
        DisplayTabView tab = new DisplayTabView();
        tab.setName("Test");

        List<DisplaySectionView> sections = new ArrayList<DisplaySectionView>();

        DisplaySectionView section = new DisplaySectionView();
        section.setName("Test");
        section.setTemplate("<span>Hello, World!</span>");

        section.setField("name");

        Filter sectionFilter = new Filter();
        sectionFilter.setField("type");
        sectionFilter.setValue("Test");

        List<Filter> sectionFilters = new ArrayList<Filter>();
        sectionFilters.add(sectionFilter);

        section.setFilters(sectionFilters);

        Sort sectionSort = new Sort();
        sectionSort.setField("name");
        sectionSort.setDirection(Direction.ASC);

        List<Sort> sectionSorting = new ArrayList<Sort>();
        sectionSorting.add(sectionSort);

        section.setSort(sectionSorting);

        List<String> requiredFields = new ArrayList<String>();
        requiredFields.add("type");

        section.setRequiredFields(requiredFields);

        List<String> lazyReferences = new ArrayList<String>();
        lazyReferences.add("publications");

        section.setLazyReferences(lazyReferences);

        DisplaySubsectionView subsection = new DisplaySubsectionView();

        subsection.setName("Test");
        subsection.setField("publications");

        Filter subsectionFilter = new Filter();
        subsectionFilter.setField("type");
        subsectionFilter.setValue("Test");

        List<Filter> subsectionFilters = new ArrayList<Filter>();
        subsectionFilters.add(subsectionFilter);

        subsection.setFilters(subsectionFilters);

        Sort subsectionSort = new Sort();
        subsectionSort.setField("date");
        subsectionSort.setDirection(Direction.DESC);

        List<Sort> subsectionSorting = new ArrayList<Sort>();
        subsectionSorting.add(subsectionSort);

        subsection.setSort(subsectionSorting);

        subsection.setTemplate("<div>Subsection</div>");

        List<DisplaySubsectionView> subsections = new ArrayList<DisplaySubsectionView>();
        subsections.add(subsection);

        section.setSubsections(subsections);

        sections.add(section);

        tab.setSections(sections);

        return tab;
    }

    public static ExportView getMockExportView() {
        ExportView export = new ExportView();

        export.setName("Test");

        export.setContentTemplate("<html><body><span>Hello, Content!</span></body></html>");
        export.setHeaderTemplate("<html><body><span>Hello, Header!</span></body></html>");

        List<String> requiredFields = new ArrayList<String>();
        requiredFields.add("type");

        export.setRequiredFields(requiredFields);

        List<String> lazyReferences = new ArrayList<String>();
        lazyReferences.add("publications");

        export.setLazyReferences(lazyReferences);

        List<ExportFieldView> fieldViews = new ArrayList<ExportFieldView>();

        ExportFieldView exportField = new ExportFieldView();

        exportField.setName("Test");
        exportField.setField("publications");

        Filter filter = new Filter();
        filter.setField("type");
        filter.setValue("Test");

        List<Filter> filters = new ArrayList<Filter>();
        filters.add(filter);

        exportField.setFilters(filters);

        Sort sort = new Sort();
        sort.setField("date");
        sort.setDirection(Direction.DESC);
        sort.setDate(true);

        List<Sort> sorting = new ArrayList<Sort>();
        sorting.add(sort);

        exportField.setSort(sorting);

        exportField.setLimit(10);

        fieldViews.add(exportField);

        export.setFieldViews(fieldViews);

        return export;
    }

}
