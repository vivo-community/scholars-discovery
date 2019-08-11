export class RestService {

    // NOTE: request may occur faster then responses are cached
    // TODO: queue duplicate requests to resolve together
    private cache = {};

    protected get(url: string): Promise<any> {
        return new Promise((resolve, reject) => {
            if (this.cache[url]) {
                resolve(this.cache[url]);
                return;
            }
            const xhr = new XMLHttpRequest();
            xhr.open('GET', url, true);
            xhr.onload = () => {
                if (xhr.status >= 200) {
                    this.cache[url] = JSON.parse(xhr.response);
                    resolve(this.cache[url]);
                } else {
                    reject(xhr.status);
                }
            };
            xhr.onerror = () => {
                reject(`An error occured for request GET ${url}`);
            };
            xhr.send();
        });
    }

}
