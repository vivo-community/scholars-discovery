import {renderTemplate, initializeTemplateHelpers} from './utilities/template.utility';

$('.scholars-embed').each(function() {
    const $embed: any = $(this);
    const id: string = $embed.data('id');
    const displayViewName: any = $embed.data('displayview');
    const displayCollection: any = $embed.data('collection');
    const sections: any = $embed.data('sections').split(',');
    const templates: string[] = [];

    const uriHost = 'http://localhost:9000/'; /* FIXME: this should be generated when this script gets generated. */
    const uriDisplayByType = 'displayViews/search/findByTypesIn?types=';
    const uriDisplayByName = 'displayViews/search/findByName?name=';

    var displayView: any = {};
    var mainSolrDocoument: any = {};
    var solrDocuments: any = {};

    var processDisplayView = function () {
        $.ajax(uriHost + uriDisplayByName + displayViewName).then(function (responseDisplayView: any) {
            displayView = responseDisplayView;
            $.ajax(uriHost + displayCollection + '/' + id).then(function(responseSolrDocument: any) {
                mainSolrDocoument = responseSolrDocument;
                processSolrDocument();
            });
        });
    };

    var processSolrDocument = function () {
        $.each(sections, function(i: any, section: any) {
            $.each(displayView.tabs, function(j: any, tab: any) {
                $.each(tab.sections, function(k: any, tabSection: any) {
                    if (tabSection.name === section) {
                        processTabSectionTemplates(tabSection);
                        return false;
                    }
                });
            });
        });

        const markup: any = renderTemplate(templates.join(' '), mainSolrDocoument);
        $embed.html(markup);
    };

    var processTabSectionTemplates = function (tabSection: any) {
        var references: any = {};
        var subTemplate: any;
        var fieldIndex: any;

        if (tabSection.hasOwnProperty("template")) {
            templates.push(tabSection.template);
        }

        $.each(tabSection.lazyReferences, function(i: any, lazyReference: any) {
            references[lazyReference.field] = lazyReference.collection;
        });

        $.each(tabSection.subsections, function(i: any, subSection: any) {
            if (subSection.hasOwnProperty('template') && subSection.hasOwnProperty('field')) {
                if (references.hasOwnProperty(subSection.field)) {
                    for (fieldIndex in mainSolrDocoument[subSection.field]) {
                        let document: any = getSolrDocument(references[subSection.field], mainSolrDocoument[subSection.field][fieldIndex].id);
                        let renderred: any = renderTemplate(subSection.template, document);
                        templates.push(renderred);
                    }
                }
                else if (mainSolrDocoument.hasOwnProperty(subSection.field)) {
                    for (fieldIndex in mainSolrDocoument[subSection.field]) {
                        let document: any = mainSolrDocoument[subSection.field][fieldIndex];
                        let renderred: any = renderTemplate(subSection.template, document);
                        templates.push(renderred);
                    }
                }
            }
        });
    };

    // temporary sycnhronious implementation, FIXME: needs async that block until all requests are done.
    var getSolrDocument = function (collection: any, id: any) {
        if (!solrDocuments.hasOwnProperty(id)) {
            const request: any = {
                type: 'GET',
                url: uriHost + collection + '/' + id,
                async: false,
                success: function(document: any) {
                    solrDocuments[id] = document;
                }
            };

            $.ajax(request);
        }

        return solrDocuments[id];
    };

    initializeTemplateHelpers();
    processDisplayView();
});
