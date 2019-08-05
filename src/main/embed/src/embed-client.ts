import { EmbedSection } from './embed-section';

export class EmbedClient {

    public load(): Promise<void> {
        return new Promise((resolve, reject) => {
            const elements: any[] = [].slice.call(document.getElementsByClassName('_scholars_embed_'));
            elements.map((element: any) => new EmbedSection(element))
                .reduce((previousPromise, nextSection) => previousPromise
                    .then(() => nextSection.load()), Promise.resolve());
            resolve();
        });
    }

}
