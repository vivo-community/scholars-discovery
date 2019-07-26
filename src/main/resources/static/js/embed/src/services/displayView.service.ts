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
        var responseJson = JSON.parse(response);
        this.cache[options.viewName] = responseJson;
        resolve(responseJson);
    };

    private onFailure = (response: any, options: Options, reject: Function) => {
        reject(response);
    };

    private request = (options: Options, resolve: Function, reject: Function): void => {
        const service = this;

        const request = new XMLHttpRequest();

        request.open("GET", environment.middleware.baseUrl + environment.middleware.displayViewByName + options.viewName);

        request.addEventListener('load', function () {
            if (this.readyState === 4 && this.status === 200) {
                service.onSuccess(request.responseText, options, resolve);
            } else {
                service.onFailure(request.responseText, options, reject);
            }
        });

        request.addEventListener('error', function () {
            service.onFailure(request.responseText, options, reject);
        });

        request.send();
    }
}
