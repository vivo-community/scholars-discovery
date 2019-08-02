import { environment } from './environments/environment';

import { initializeTemplateHelpers } from 'scholars-embed-utilities';

import { EmbedClient } from './embed-client';

const load = () => {
    initializeTemplateHelpers(environment.formalize);
    new EmbedClient().load();
};

(() => load())();

export {
    load
};
