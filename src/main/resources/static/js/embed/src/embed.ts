import { Direction } from './enums/direction.enum';

import { environment } from './environments/environment.ts';

import { Sort } from './interfaces/sort.interface';

import * as DisplayView from './services/displayView.service';
import * as SolrDocument from './services/solrDocument.service';

import { renderTemplate, initializeTemplateHelpers } from './utilities/template.utility';
import { processSolrDocument } from './utilities/process.utility';

import * as Aggregate from './views/aggregate.view';
import * as Html from './views/html.view';
import * as Pagination from './views/pagination.view';
import * as Subsection from './views/subsection.view';

initializeTemplateHelpers();

const embeddables = document.getElementsByClassName("scholars-embed");
const solrDocumentRepo = new SolrDocument.Service();
const displayViewRepo = new DisplayView.Service();
const promised: any = {};

for (var i = 0; i < embeddables.length; i++) {
    (function() {
        const embed = embeddables.item(i);
        const id: string = embed.getAttribute("data-id");
        const displayViewName: any = embed.getAttribute("data-displayview");
        const displayCollection: any = embed.getAttribute("data-collection");
        const sections: any = embed.getAttribute("data-sections").split(',');
        const showHeaders: boolean = (embed.getAttribute("data-show-headers") !== null && embed.getAttribute("data-show-headers").toLowerCase().trim() === 'true') ? true:false;
        const templates: string[] = [];
        const promises: Promise<any>[] = [];

        var displayView: any = {};
        var mainSolrDocoument: any = {};

        const processDisplayView = function () {
            const dvOptions: DisplayView.Options = new DisplayView.Options(displayViewName);

            displayViewRepo.get(dvOptions).then((response: any) => {
                const sdOptions: SolrDocument.Options = new SolrDocument.Options(displayCollection, id);

                displayView = response;

                let promise: Promise<any>;
                if (promised.hasOwnProperty(sdOptions.id)) {
                    promise = promised[sdOptions.id];
                }
                else {
                    promise = solrDocumentRepo.get(sdOptions);
                    promised[sdOptions.id] = promise;
                }

                promise.then((response: any) => {
                    mainSolrDocoument = response;
                    processSolrDocument(sections, displayView, preProcessTabSectionTemplates);

                    Promise.all(promises).then((response: any) => {
                        processSolrDocument(sections, displayView, processTabSectionTemplates);
                        renderIframe(templates);
                    });
                });
            });
        };

        const renderIframe = (templates: string[]) => {
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

        const preProcessTabSectionTemplates = (section: any, tabSection: any) => {
            const references = {};
            var subTemplate: any;

            for (var i = 0; i < tabSection.lazyReferences.length; i++) {
                var lazyReference = tabSection.lazyReferences[i];
                references[lazyReference.field] = lazyReference.collection;
            }

            for (var j = 0; j < tabSection.subsections.length; j++) {
                var subSection = tabSection.subsections[j];
                if (subSection.hasOwnProperty('template') && subSection.hasOwnProperty('field')) {
                    if (references.hasOwnProperty(subSection.field)) {
                        for (let fieldIndex in mainSolrDocoument[subSection.field]) {
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
                            if (promised.hasOwnProperty(options.id)) {
                                promises.push(promised[options.id]);
                            }
                            else {
                                let promise = solrDocumentRepo.get(options);
                                promises.push(promise);
                                promised[options.id] = promise;
                            }
                        }
                    }
                }
            }
        };

        const processTabSectionTemplates = (section: any, tabSection: any) => {
            const references = {};
            var paginationId: number = 0;
            var subTemplate: any;

            const aggregate: Aggregate.View = new Aggregate.View(tabSection.name, [], showHeaders);

            if (tabSection.hasOwnProperty("template")) {
                aggregate.list.push(tabSection.template);
            }

            for (let i = 0; i < tabSection.lazyReferences.length; i++) {
                let lazyReference = tabSection.lazyReferences[i];
                references[lazyReference.field] = lazyReference.collection;
            }

            for (let i = 0; i < tabSection.subsections.length; i++) {
                let subSection = tabSection.subsections[i];
                let subsectionView: Subsection.View;

                if (subSection.hasOwnProperty('template') && subSection.hasOwnProperty('field')) {
                    if (references.hasOwnProperty(subSection.field)) {
                        paginationId++;
                        let pagination: Pagination.View = new Pagination.View(paginationId.toString(), 'TODO: add aria label here');
                        let filtered = filterSubsection(subSection);
                        let sorted = sortSubsection(filtered, subSection.sort);

                        for (let index in sorted) {
                            let renderred = renderTemplate(subSection.template, sorted[index]);
                            pagination.list.push(renderred);
                        }

                        subsectionView = new Subsection.View(paginationId.toString(), subSection.name, pagination.render(), pagination.list.length);
                        aggregate.list.push(subsectionView.render());
                    }
                    else if (mainSolrDocoument.hasOwnProperty(subSection.field)) {
                        paginationId++;
                        let pagination: Pagination.View = new Pagination.View(paginationId.toString(), 'TODO: add aria label here');

                        for (let fieldIndex in mainSolrDocoument[subSection.field]) {
                            let document: any = mainSolrDocoument[subSection.field][fieldIndex];
                            let renderred: any = renderTemplate(subSection.template, document);
                            pagination.list.push(renderred);
                        }

                        subsectionView = new Subsection.View(paginationId.toString(), subSection.name, pagination.render(), pagination.list.length);
                        aggregate.list.push(subsectionView.render());
                    }
                }
            }

            templates.push(aggregate.render());
        };

        const filterSubsection = (subSection: any): any[] => {
            const filtered: any[] = [];

            for (let index in mainSolrDocoument[subSection.field]) {
                let mainField = mainSolrDocoument[subSection.field][index];

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

                let document: any = solrDocumentRepo.getById(mainField.id);
                if (document) {
                    filtered.push(document);
                }
            }

            return filtered;
        };

        const sortSubsection = (list: any[], sort: Sort[]): any[] => {
            let sorted = [].concat(list);

            for (const s of sort) {
                const asc = Direction[s.direction] === Direction.ASC;
                sorted = sorted.sort((a, b) => {
                    const av = s.date ? new Date(a[s.field]) : a[s.field];
                    const bv = s.date ? new Date(b[s.field]) : b[s.field];

                    return asc ? (av > bv) ? 1 : ((bv > av) ? -1 : 0) : (bv > av) ? 1 : ((av > bv) ? -1 : 0);
                });
            }

            return sorted;
        };

        processDisplayView();
    })();
}
