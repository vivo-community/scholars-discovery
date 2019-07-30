import { EmbedRenderer } from './embed-renderer';

import { createIframe } from './utilities/document.utility';
import { discoveryService } from './services/discovery.service';
import { viewService } from './services/view.service';

export class EmbedSection {

    private element: any;

    constructor(element: any) {
        this.element = element;
    }

    public load(): Promise<void> {
        return new Promise((resolve, reject) => {
            const collection = this.getDataAttribute(this.element.dataset, 'collection');
            const id = this.getDataAttribute(this.element.dataset, 'individual');
            const display = this.getDataAttribute(this.element.dataset, 'display');

            Promise.all([
                createIframe(this.element),
                viewService.getDisplayView(display),
                discoveryService.getIndividual(collection, id)
            ]).then(([iframe, displayView, individual]) => {
                iframe.id = `${collection}-${id}-${btoa(display)}-${btoa(this.element.dataset.sections)}`;

                const sections = this.getDataAttribute(this.element.dataset, 'sections').split(';');
                const embedView = this.element.dataset.view !== undefined ? this.element.dataset.view : 'default';

                const renderer = new EmbedRenderer({
                    collection,
                    id,
                    display,
                    sections,
                    embedView,
                    displayView,
                    individual,
                    window: iframe.contentWindow
                });

                for (const section of this.getSections(displayView, sections)) {
                    this.fetchLazyReferences(section, individual).then(() => renderer.render(section));
                }
                resolve();
            });
        });
    }

    private getDataAttribute(dataset: any, name: string): string {
        const data = dataset[name];
        if (data === undefined) {
            throw Error(`${name} is required`);
        }
        return data;
    }

    private getSections(displayView: any, names: string[]): any[] {
        const sections = [];
        for (const name of names) {
            sections.push(this.getSection(displayView, name));
        }
        return sections;
    }

    private getSection(displayView: any, name: string): any {
        for (const tab of displayView.tabs) {
            for (const section of tab.sections) {
                if (section.name.toLowerCase().trim() === name.toLowerCase().trim()) {
                    return section;
                }
            }
        }
        throw Error(`No section with name ${name}`);
    }

    private fetchLazyReferences(section: any, individual: any): Promise<void> {
        return new Promise((resolve, reject) => {
            const promises: Array<Promise<any>> = [];
            for (const lazyReference of section.lazyReferences) {
                const ids = individual[lazyReference.field].map((resource) => resource.id);
                const promise = discoveryService.getByIdIn(lazyReference.collection, ids);
                promises.push(promise);
                promise.then((resources: any[]) => {
                    individual[lazyReference.field] = resources;
                });
            }
            Promise.all(promises).then(() => resolve());
        });
    }

}
