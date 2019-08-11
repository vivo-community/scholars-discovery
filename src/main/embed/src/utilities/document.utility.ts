import { environment } from '../environments/environment';

const iframeBaseHref = `${environment.uiUrl}/`;

const iframeStylesheets = [
    `${environment.serviceUrl}/embed/bootstrap.min.css`
];

const iframeScripts = [
    `${environment.serviceUrl}/embed/vendor-bundle.min.js`
];

const createIframe = (element: any): Promise<HTMLIFrameElement> => {
    return new Promise((resolve) => {
        const iframe = document.createElement('iframe');
        iframe.src = 'about:blank';
        iframe.frameBorder = '0';
        iframe.style.width = '100%';
        iframe.style.height = '100%';
        iframe.scrolling = 'auto';
        iframe.onload = () => {
            const iframeBody = iframe.contentWindow.document.body;
            const iframeHead = iframe.contentWindow.document.head;

            iframeBody.style.fontFamily = 'font-family: \'Lato\', Calibri, Arial, sans-serif';
            iframeBody.style.margin = '5px';

            loadBaseHref(iframeHead, iframeBaseHref);
            loadStyles(iframeHead, iframeStylesheets);
            loadScripts(iframeHead, iframeScripts).then(() => {
                resolve(iframe);
            });
        };

        const wrapper = createWrapperElement();
        const style = createStyleElement();

        element.appendChild(style);
        element.appendChild(wrapper);

        wrapper.appendChild(iframe);
    });
};

const loadBaseHref = (head: HTMLHeadElement, url: string): Promise<void> => {
    return new Promise((resolve) => {
        const tag = document.createElement('base');
        tag.href = url;
        tag.onload = () => resolve();
        head.appendChild(tag);
    });
};

const loadStyle = (head: HTMLHeadElement, href: string): Promise<void> => {
    return new Promise((resolve) => {
        const tag = document.createElement('link');
        tag.rel = 'stylesheet';
        tag.href = href;
        tag.onload = () => resolve();
        head.appendChild(tag);
    });
};

const loadStyles = (head: HTMLHeadElement, styles: string[]): Promise<void> => {
    return styles.reduce((previousPromise, nextUrl) => previousPromise
        .then(() => loadStyle(head, nextUrl)), Promise.resolve());
};

const loadScript = (element: HTMLElement, src: string): Promise<void> => {
    return new Promise((resolve) => {
        const tag = document.createElement('script');
        tag.src = src;
        tag.onload = () => resolve();
        element.appendChild(tag);
    });
};

const loadScripts = (element: HTMLElement, scripts: string[]): Promise<void> => {
    return scripts.reduce((previousPromise, nextScript) => previousPromise
        .then(() => loadScript(element, nextScript)), Promise.resolve());
};

const createWrapperElement = (): any => {
    const element = document.createElement('div');
    element.classList.add('_scholars_embed_wrapper_');
    element.setAttribute('style', '-webkit-overflow-scrolling: touch; scrollbar-width: none; -ms-overflow-style: none;');
    element.style.width = '100%';
    element.style.height = '100%';
    element.style.overflow = 'auto';
    return element;
};

const createStyleElement = (): any => {
    const element = document.createElement('style');
    element.type = 'text/css';
    element.innerHTML = '._scholars_embed_wrapper_::-webkit-scrollbar { width: 0px; }';
    return element;
};

const createSectionElement = (): any => {
    return document.createElement('div');
};

const createSubsectionElement = (): any => {
    return document.createElement('div');
};

const setLinkTargets = (document: any, target: string = '_blank') => {
    for (const link of document.links) {
        link.target = target;
    }
};

export {
    createIframe,
    createSectionElement,
    createSubsectionElement,
    setLinkTargets
};
