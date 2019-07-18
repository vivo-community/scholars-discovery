import {renderTemplate, initializeTemplateHelpers} from './utilities/template.utility';
import { environment } from './environments/environment.ts';

import * as DisplayView from './services/displayView.service';
import * as SolrDocument from './services/solrDocument.service';

$('.scholars-embed').each(function() {
    const $embed: any = $(this);
    const id: string = $embed.data('id');
    const displayViewName: any = $embed.data('displayview');
    const displayCollection: any = $embed.data('collection');
    const sections: any = $embed.data('sections').split(',');
    const templates: string[] = [];
    const promises: Promise<any>[] = [];

    var displayView: any = {};
    var mainSolrDocoument: any = {};
    var solrDocuments: any = {};

    const solrDocumentRepo = new SolrDocument.Service();
    const displayViewRepo = new DisplayView.Service();

    var processDisplayView = function () {
        const dvOptions: DisplayView.Options = new DisplayView.Options(displayViewName);

        displayViewRepo.get(dvOptions).then((response: any) => {
            const sdOptions: SolrDocument.Options = new SolrDocument.Options(displayCollection, id);

            displayView = response;

            solrDocumentRepo.get(sdOptions).then((response: any) => {
                mainSolrDocoument = response;
                preProcessSolrDocument();

                Promise.all(promises).then((response: any) => {
                    processSolrDocument();
                });
            });
        });
    };

    // TODO: rewrite to remove redudancy in designs between preProcessX and processX calls (and related).
    var preProcessSolrDocument = function () {
        $.each(sections, function(i: any, section: any) {
            $.each(displayView.tabs, function(j: any, tab: any) {
                $.each(tab.sections, function(k: any, tabSection: any) {
                    if (tabSection.name === section) {
                        preProcessTabSectionTemplates(tabSection);
                        return false;
                    }
                });
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
        renderIframe(templates);
    };

    var renderIframe = function(templates: string[]) {
        var iframe = document.createElement('iframe');
        iframe.style.width = '100%';
        iframe.style.height = '100%';
        var html = '<head>'+
                 '    <base href="' + environment.ngAppUrl + '">'+
                 '    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" />'+
                 '    <script type="text/javascript" src=""></script>'+
                 '    <style type="text/css">'+
                 '      :root{'+
                 '        --primary: #500000;--link-color: #2b5d7d;'+
                 '      }'+
                 '      body a {color: var(--link-color);}'+
                 '      .text-primary {color: var(--primary) !important;}'+
                 '    </style>'+
                 '  </head>'+
                 '  <body>'+renderTemplate(templates.join(' '), mainSolrDocoument)+'</body>';
        $embed.append(iframe);
        iframe.contentWindow.document.open();
        iframe.contentWindow.document.write(html);
        iframe.contentWindow.document.close();
    };

    var preProcessTabSectionTemplates = function (tabSection: any) {
        var references = {};
        var subTemplate: any;
        var fieldIndex: any;

        $.each(tabSection.lazyReferences, function(i: any, lazyReference: any) {
            references[lazyReference.field] = lazyReference.collection;
        });

        $.each(tabSection.subsections, function(i: any, subSection: any) {
            if (subSection.hasOwnProperty('template') && subSection.hasOwnProperty('field')) {
                if (references.hasOwnProperty(subSection.field)) {
                    for (fieldIndex in mainSolrDocoument[subSection.field]) {
                        let mainField = mainSolrDocoument[subSection.field][fieldIndex];

                        // filter out all main document fields designated by the display view subsection.
                        if (subSection.hasOwnProperty('filters') && subSection.filters.length > 0) {
                            let isFilteredOut: boolean = false;
                            for (let filterIndex in subSection.filters) {
                                let filterField = subSection.filters[filterIndex].field;
                                let filterValue = subSection.filters[filterIndex].value;

                                if (!mainField.hasOwnProperty(filterField)) {
                                    isFilteredOut = true;
                                    break;
                                }

                                if (mainField[filterField] !== filterValue) {
                                    isFilteredOut = true;
                                    break;
                                }
                            }

                            if (isFilteredOut) continue;
                        }

                        let options: SolrDocument.Options = new SolrDocument.Options(references[subSection.field], mainField.id);
                        promises.push(solrDocumentRepo.get(options));
                    }
                }
            }
        });
    };

    var processTabSectionTemplates = function (tabSection: any) {
        var references = {};
        var subTemplate: any;
        var fieldIndex: any;

        var aggregateTemplate = '<div class="ml-3 mr-3 mt-3 card">'+
                                '  <div class="card-header font-weight-bold text-primary text-capitalize">'+tabSection.name+'</div>'+
                                '  <div class="card-body">';

        if (tabSection.hasOwnProperty("template")) {
            aggregateTemplate += tabSection.template;
        }

        $.each(tabSection.lazyReferences, function(i: any, lazyReference: any) {
            references[lazyReference.field] = lazyReference.collection;
        });

        $.each(tabSection.subsections, function(i: any, subSection: any) {
            if (subSection.hasOwnProperty('template') && subSection.hasOwnProperty('field')) {
                if (references.hasOwnProperty(subSection.field)) {
                    for (fieldIndex in mainSolrDocoument[subSection.field]) {
                        let mainField = mainSolrDocoument[subSection.field][fieldIndex];

                        // filter out all main document fields designated by the display view subsection.
                        if (subSection.hasOwnProperty('filters') && subSection.filters.length > 0) {
                            let isFilteredOut: boolean = false;
                            for (let filterIndex in subSection.filters) {
                                let filterField = subSection.filters[filterIndex].field;
                                let filterValue = subSection.filters[filterIndex].value;

                                if (!mainField.hasOwnProperty(filterField)) {
                                    isFilteredOut = true;
                                    break;
                                }

                                if (mainField[filterField] !== filterValue) {
                                    isFilteredOut = true;
                                    break;
                                }
                            }

                            if (isFilteredOut) continue;
                        }

                        let document = solrDocumentRepo.getById(mainField.id);
                        let renderred = renderTemplate(subSection.template, document);
                        aggregateTemplate += renderred;
                    }
                }
                else if (mainSolrDocoument.hasOwnProperty(subSection.field)) {
                    for (fieldIndex in mainSolrDocoument[subSection.field]) {
                        let document: any = mainSolrDocoument[subSection.field][fieldIndex];
                        let renderred: any = renderTemplate(subSection.template, document);
                        aggregateTemplate += renderred;
                    }
                }
            }
        });

        aggregateTemplate +=     '</div>'+
                             '</div>';
        templates.push(aggregateTemplate);
    };

    initializeTemplateHelpers();
    processDisplayView();
});
