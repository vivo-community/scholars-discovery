import { environment } from '../environments/environment.ts';

export class View {
    public name: string;
    public list: string[];

    constructor(name?: string, list?: string[]) {
        this.name = name ? name : '';
        this.list = list ? list : [];
    };

    public render = (): string => {
        var markup = '<div class="ml-3 mr-3 mt-3 card">' +
                     '  <div class="card-header font-weight-bold text-primary text-capitalize">' + this.name + '</div>' +
                     '  <div class="card-body">';

        for (let i in this.list) {
            markup += this.list[i];
        }

        markup +=    '  </div>' +
                     '</div>';

        return markup;
    };
}
