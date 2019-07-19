import { environment } from '../environments/environment.ts';

export class Options {
    public collection: string;
    public id: string;

    constructor(collection?: string, id?: string) {
        this.collection = collection ? collection : '';
        this.id = id ? id : '';
    }
}

export class Service {
    public get = (options: Options): any => {
        const service = this;

        let promise = new Promise((resolve: Function, reject: Function) => {
            if (service.cache.hasOwnProperty(options.id)) {
                resolve(service.cache[options.id]);
            }
            else {
                service.request(options, resolve, reject);
            }
        });

        return promise;
    };

    // TODO: temporarily intentonaly only loads cached, this should be redesigned.
    public getById = (id: string): any => {
        if (this.cache.hasOwnProperty(id)) {
            return this.cache[id];
        }

        return {};
    };

    private cache = {};

    private onSuccess = (response: any, options: Options, resolve: Function) => {
        var responseJson = JSON.parse(response);
        this.cache[options.id] = responseJson;
        resolve(responseJson);
    };

    private onFailure = (response: any, options: Options, reject: Function) => {
        reject(response);
    };

    private request = (options: Options, resolve: Function, reject: Function): void => {
        const service = this;

        const request = new XMLHttpRequest();

        request.open("GET", environment.middleware.baseUrl + '/' + options.collection + '/' + options.id);

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
