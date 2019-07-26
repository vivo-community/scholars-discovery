import { compile, registerHelper } from 'handlebars';

import { formalize } from './formalize.pipe';

import { environment } from '../environments/environment.ts';

const cache: Map<number, any> = new Map<number, any>();

const initializeTemplateHelpers = () => {
    registerHelper('formalize', (value: string | string[]) => formalize(value));
    registerHelper('replace', (value: string, arg1: string, arg2: string) => {
        if (Array.isArray(value)) {
            const values = [];
            for (const entry of value) {
                values.push(entry.replace(arg1, arg2));
            }
            return values;
        }
        return value.replace(arg1, arg2);
    });
    registerHelper('ifEquals', (arg1, arg2, options) => (arg1 === arg2) ? options.fn(this) : options.inverse(this));
    registerHelper('toYear', (value: string) => value !== undefined ? new Date(value).getFullYear() : value);
    registerHelper('toDate', (value: string) => value !== undefined ? new Date(value).toISOString() : value);
    registerHelper('workByStudent', (workByStudent: any, options) => {
        if (workByStudent.label) {
            const parts = workByStudent.label.match(/(^.*\)\.) (.*?\.) ([Master's|Doctoral].*\.$)/);
            if (parts) {
                if (parts.length > 1) {
                    workByStudent.authorAndDate = parts[1];
                }
                if (parts.length > 2) {
                    workByStudent.title = parts[2];
                }
                if (parts.length > 3) {
                    workByStudent.degree = parts[3];
                    if (workByStudent.degree.endsWith('.')) {
                        workByStudent.degree = workByStudent.degree.substring(0, workByStudent.degree.length - 1);
                    }
                }
            }
        }
        return options.fn(workByStudent);
    });
    registerHelper('showPositionForPreferredTitle', (positions, preferredTitle, options) => {
        const positionsCount = positions.length;
        let organizationForTitle;
        for (let i = 0; i < positionsCount; i++) {
            const position = positions[i];
            if (position.label === preferredTitle) {
                organizationForTitle = position.organizations[0];
                break;
            }
        }
        return options.fn(organizationForTitle);
    });
    registerHelper('eachSortedPosition', (positions, hrJobTitle, options) => {
        function positionSorter(labelCheck) {
            return (a, b) => {
                if (a.label === labelCheck) {
                    return -1;
                } else if (b.label === labelCheck) {
                    return 1;
                }
                return 0;
            };
        }
        positions = positions.sort(positionSorter(hrJobTitle));
        let out = '';
        for (const i in positions) {
            if (positions.hasOwnProperty(i)) {
                out += options.fn(positions[i]);
            }
        }
        return out;
    });
    registerHelper('json', function(obj) {
        return JSON.stringify(obj, null, 3);
    });
};

const hashCode = (value) => {
    let hash;
    for (let i = 0; i < value.length; i++) {
        // tslint:disable-next-line: no-bitwise
        hash = Math.imul(31, hash) + value.charCodeAt(i) | 0;
    }
    return hash;
};

const compileTemplate = (template) => {
    if (template && template.length > 0) {
        const hash = hashCode(template);
        let templateFunction = cache.get(hash);
        if (templateFunction === undefined) {
            templateFunction = compile(template);
            cache.set(hash, templateFunction);
        }
        return templateFunction;
    }
    return (context) => '';
};

const renderTemplate = (template, resource) => {
    const templateFunction = compileTemplate(template);
    return templateFunction(resource);
};

const getTemplateFunction = (template: string) => (resource: any) => {
    resource.vivoUrl = environment.vivoUrl;
    return renderTemplate(template, resource);
};

const getParsedTemplateFunction = (template: string) => {
    compileTemplate(template);
    return getTemplateFunction(template);
};

export {
    renderTemplate,
    initializeTemplateHelpers
};
