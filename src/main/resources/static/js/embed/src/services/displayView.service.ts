import { environment } from '../environments/environment.ts';

export class Options {
    public viewName: string;

    constructor(viewName?: string) {
        this.viewName = viewName ? viewName : '';
    }
}

export class Service {
    public get = (options: Options): Promise<any> => {
        const service = this;

        let promise = new Promise((resolve: Function, reject: Function) => {
            if (service.cache.hasOwnProperty(options.viewName)) {
                resolve(service.cache[options.viewName]);
            }
            else {
                service.request(options, resolve, reject);
            }
        });

        return promise;
    };

    private cache = {};

    private onSuccess = (response: any, options: Options, resolve: Function) => {
        this.cache[options.viewName] = response;
        resolve(response);
    };

    private onFailure = (response: any, options: Options, reject: Function) => {
        reject(response);
    };

    private request = (options: Options, resolve: Function, reject: Function): void => {
        const service = this;

        const request = {
            type: 'GET',
            url: environment.middleware.baseUrl + environment.middleware.displayViewByName + options.viewName,
            success: function (response: any) {
                service.onSuccess(response, options, resolve);
            },
            error: function (response: any) {
                service.onFailure(response, options, reject);
            }
        };

        $.ajax(request);
    }
}
