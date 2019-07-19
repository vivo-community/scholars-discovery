import { environment } from '../environments/environment.ts';

export class View {
    public name: string;
    public list: string[];
    public includeHeaders;

    constructor(name?: string, list?: string[], includeHeaders?: boolean) {
        this.name = name ? name : '';
        this.list = list ? list : [];
        this.includeHeaders = includeHeaders ? includeHeaders : false;
    };

    public render = (): string => {
        if (this.includeHeaders) {
            var markup = '<div class="ml-3 mr-3 mt-3 card">' +
                         '  <div class="card-header font-weight-bold text-primary text-capitalize">' + this.name + '</div>' +
                         '  <div class="card-body">';

            for (let i in this.list) {
                markup += this.list[i];
            }

            markup +=    '  </div>' +
                         '</div>';
        } else {
            var markup = '<div>';
            for (let i in this.list) {
                markup += this.list[i];
            }
            markup += '   </div>';
        }
        return markup;
    };
}
