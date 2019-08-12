import { RestService } from './rest.service';

import { environment } from '../environments/environment';

const chunkSize = 200;

class DiscoveryService extends RestService {

    public getIndividual(collection: string, id: string): Promise<any> {
        return this.get(`${environment.serviceUrl}/${collection}/${id}`);
    }

    public getByIdIn(collection: string, ids: string[]): Promise<any[]> {
        return new Promise((resolve, reject) => {
            const batches = ids.map((e, i) => i % chunkSize === 0 ? ids.slice(i, i + chunkSize) : null).filter((e) => e);
            const promises: Array<Promise<any>> = batches.map((batch: string[]) => this.get(`${environment.serviceUrl}/${collection}/search/findByIdIn?ids=${batch.join(',')}`));
            Promise.all(promises).then((responses: any[]) => resolve([].concat.apply([], responses.map((res) => res._embedded[collection]))));
        });
    }

}

export const discoveryService = new DiscoveryService();
