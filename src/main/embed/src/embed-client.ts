import { EmbedSection } from './embed-section';

export class EmbedClient {

    public load(): void {
        const elements: any[] = [].slice.call(document.getElementsByClassName('_scholars_embed_'));
        elements.map((element: any) => new EmbedSection(element))
            .reduce((previousPromise, nextSection) => previousPromise
                .then(() => nextSection.load()), Promise.resolve());
    }

}
