import { RestService } from './rest.service';

import { environment } from '../environments/environment';

class ViewService extends RestService {

    public getDisplayView(name: string): Promise<any> {
        return this.get(`${environment.serviceUrl}/displayViews/search/findByName?name=${name}`);
    }

}

export const viewService = new ViewService();
