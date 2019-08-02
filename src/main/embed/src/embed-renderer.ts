import { getParsedTemplateFunction, pagination } from 'scholars-embed-utilities';

import { environment } from './environments/environment';

import { Embedded } from './model/embedded';

import { setLinkTargets, createSectionElement, createSubsectionElement } from './utilities/document.utility';

const additionalContext = {
    vivoUrl: environment.vivoUrl,
    serviceUrl: environment.serviceUrl
};

export class EmbedRenderer {

    private embedded: Embedded;

    constructor(embedded: Embedded) {
        this.embedded = embedded;
    }

    public render(section: any): Promise<void> {
        return new Promise((resolve, reject) => {
            const sectionTemplateFunction = getParsedTemplateFunction(section.template, additionalContext);
            const sectionElement = createSectionElement();
            sectionElement.innerHTML = sectionTemplateFunction(this.embedded.individual);
            this.embedded.window.document.body.appendChild(sectionElement);
            setLinkTargets(this.embedded.window.document);
            for (const subsection of section.subsections) {
                this.renderSubsection(subsection, 1, subsection.pageSize);
            }
            resolve();
        });
    }

    public renderSubsection(subsection: any, pageNumber: number, pageSize: number): void {
        const embedTemplate = this.embedded.displayView.embedTemplates[this.embedded.embedView];

        const subsectionTemplateFunction = getParsedTemplateFunction(embedTemplate, additionalContext);

        const identifier = `${this.embedded.collection}-${this.embedded.individual.id}-${btoa(this.embedded.displayView.name)}-${btoa(subsection.name)}`;

        const subsectionHTML = this.processSubsection(identifier, subsection, subsectionTemplateFunction, pageNumber, pageSize);

        if (subsectionHTML) {
            let subsectionElement = this.embedded.window.document.getElementById(identifier);
            if (!subsectionElement) {
                subsectionElement = createSubsectionElement();
                subsectionElement.id = identifier;
                this.embedded.window.document.body.appendChild(subsectionElement);
            }

            subsectionElement.innerHTML = subsectionHTML;

            const pageSizeControls = this.embedded.window.document.getElementsByClassName(`page-size-${identifier}`);
            if (pageSizeControls.length) {
                const pageSizeButtons = pageSizeControls[0].querySelectorAll('button');
                for (const pageSizeButton of pageSizeButtons) {
                    pageSizeButton.addEventListener('click', (event) => this.setPageSize(event, subsection));
                }
            }

            const pageItems = this.embedded.window.document.getElementsByClassName(`page-${identifier}`);
            if (pageItems.length) {
                for (const pageItem of pageItems) {
                    const pageLinks = pageItem.querySelectorAll('a');
                    for (const pageLink of pageLinks) {
                        pageLink.addEventListener('click', (event) => this.gotoPage(event, subsection));
                    }
                }
            }

            setLinkTargets(this.embedded.window.document);

            if (this.embedded.window._altmetric_embed_init) {
                this.embedded.window._altmetric_embed_init();
            }

            if (this.embedded.window.__dimensions_embed) {
                this.embedded.window.__dimensions_embed.addBadges();
            }
        }
    }

    private processSubsection(identifier: string, subsection: any, subsectionTemplateFunction: (context: any) => string, pageNumber: number, pageSize: number): string {
        // filter
        let resources = this.embedded.individual[subsection.field].filter((r) => {
            for (const f of subsection.filters) {
                if ((Array.isArray(r[f.field]) ? r[f.field].indexOf(f.value) < 0 : r[f.field] !== f.value)) {
                    return false;
                }
            }
            return true;
        });

        if (!resources.length) {
            return;
        }

        // sort
        resources = [].concat(resources);
        for (const s of subsection.sort) {
            const asc = s.direction.toUpperCase().trim() === 'ASC';
            resources = resources.sort((a, b) => {
                const av = s.date ? new Date(a[s.field]) : a[s.field];
                const bv = s.date ? new Date(b[s.field]) : b[s.field];
                return asc ? (av > bv) ? 1 : ((bv > av) ? -1 : 0) : (bv > av) ? 1 : ((av > bv) ? -1 : 0);
            });
        }

        // pagination
        const totalElements = resources.length;
        const totalPages = Math.ceil(totalElements / pageSize);

        const page = {
            number: pageNumber,
            size: pageSize,
            totalElements,
            totalPages
        };

        const pages = pagination(page, {
            width: document.body.clientWidth,
            height: document.body.clientHeight
        });

        return subsectionTemplateFunction({
            identifier,
            subsection,
            resources,
            page,
            pages
        });
    }

    private gotoPage(event: any, subsection: any): void {
        const dataset = Object.keys(event.target.dataset).length ? event.target.dataset : event.target.parentElement.dataset;
        const pageNumber = Number(dataset.pageNumber);
        const pageSize = Number(dataset.pageSize);
        this.renderSubsection(subsection, pageNumber, pageSize);
    }

    private setPageSize(event: any, subsection: any): void {
        this.renderSubsection(subsection, 1, Number(event.target.value));
    }

}
