import {renderTemplate, initializeTemplateHelpers} from './utilities/template.utility';
import { environment } from './environments/environment.ts';

import * as DisplayView from './services/displayView.service';
import * as SolrDocument from './services/solrDocument.service';

import * as Aggregate from './views/aggregate.view';
import * as Html from './views/html.view';
import * as Pagination from './views/pagination.view';
import * as Subsection from './views/subsection.view';

initializeTemplateHelpers();

const embeddables = document.getElementsByClassName("scholars-embed");

for (var i=0;i<embeddables.length;i++) {
    (function() {
        let embed = embeddables.item(i);
        const id: string = embed.getAttribute("data-id");
        const displayViewName: any = embed.getAttribute("data-displayview");
        const displayCollection: any = embed.getAttribute("data-collection");
        const sections: any = embed.getAttribute("data-sections").split(',');
        const showHeaders: boolean = (embed.getAttribute("data-show-headers") !== null && embed.getAttribute("data-show-headers").toLowerCase().trim() === 'true') ? true:false;
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
            for (var i=0;i<sections.length;i++) {
                var section = sections[i];
                for (var j=0;j<displayView.tabs.length;j++) {
                    var tab = displayView.tabs[j];
                    for (var k=0;k<tab.sections.length;k++) {
                        var tabSection = tab.sections[k];
                        if (tabSection.name === section) {
                            preProcessTabSectionTemplates(tabSection);
                            break;
                        }
                    }
                }

            }
        };

        var processSolrDocument = function () {
            for (var i=0;i<sections.length;i++) {
                var section = sections[i];
                for (var j=0;j<displayView.tabs.length;j++) {
                    var tab = displayView.tabs[j];
                    for (var k=0;k<tab.sections.length;k++) {
                        var tabSection = tab.sections[k];
                        if (tabSection.name === section) {
                            processTabSectionTemplates(tabSection);
                            break;
                        }
                    }
                }

            }
            renderIframe(templates);
        };

        var renderIframe = function(templates: string[]) {
            const html: Html.View = new Html.View(renderTemplate(templates.join(' '), mainSolrDocoument));
            const iframe: any = document.createElement('iframe');
            iframe.style.width = '100%';
            iframe.style.height = '100%';
            iframe.frameBorder = 0;
            embed.appendChild(iframe);
            iframe.contentWindow.document.open();
            iframe.contentWindow.document.write(html.render());
            iframe.contentWindow.document.close();


        };

        var preProcessTabSectionTemplates = function (tabSection: any) {
            var references = {};
            var subTemplate: any;
            var fieldIndex: any;

            for (var i=0;i<tabSection.lazyReferences.length;i++) {
                var lazyReference = tabSection.lazyReferences[i];
                references[lazyReference.field] = lazyReference.collection;
            }

            for (var j=0;j<tabSection.subsections.length;j++) {
                var subSection = tabSection.subsections[j];
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
            }
        };

        var processTabSectionTemplates = function (tabSection: any) {
            var references = {};
            var subTemplate: any;
            var fieldIndex: any;
            var paginationId: number = 0;

            var aggregate: Aggregate.View = new Aggregate.View(tabSection.name, [], showHeaders);

            if (tabSection.hasOwnProperty("template")) {
                aggregate.list.push(tabSection.template);
            }

            for (var i=0;i<tabSection.lazyReferences.length;i++) {
                var lazyReference = tabSection.lazyReferences[i];
                references[lazyReference.field] = lazyReference.collection;
            }

            for (var i=0;i<tabSection.subsections.length;i++) {
                var subSection = tabSection.subsections[i];
                let subsectionView: Subsection.View;
                if (subSection.hasOwnProperty('template') && subSection.hasOwnProperty('field')) {
                    if (references.hasOwnProperty(subSection.field)) {
                        paginationId++;
                        let pagination: Pagination.View = new Pagination.View(paginationId.toString(), "TODO: add aria label here");

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
                            pagination.list.push(renderred);
                        }

                        subsectionView = new Subsection.View(subSection.name, pagination.render(), pagination.list.length);
                        aggregate.list.push(subsectionView.render());
                    }
                    else if (mainSolrDocoument.hasOwnProperty(subSection.field)) {
                        paginationId++;
                        let pagination: Pagination.View = new Pagination.View(paginationId.toString(), "TODO: add aria label here");
                        for (fieldIndex in mainSolrDocoument[subSection.field]) {
                            let document: any = mainSolrDocoument[subSection.field][fieldIndex];
                            let renderred: any = renderTemplate(subSection.template, document);
                            pagination.list.push(renderred);
                        }

                        subsectionView = new Subsection.View(subSection.name, pagination.render(), pagination.list.length);
                        aggregate.list.push(subsectionView.render());
                    }
                }
            }

            templates.push(aggregate.render());
        };

        processDisplayView();
    })();
}
