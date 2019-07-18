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
        this.cache[options.id] = response;
        resolve(response);
    };

    private onFailure = (response: any, options: Options, reject: Function) => {
        reject(response);
    };

    private request = (options: Options, resolve: Function, reject: Function): void => {
        const service = this;

        const request = {
            type: 'GET',
            url: environment.middleware.baseUrl + '/' + options.collection + '/' + options.id,
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
