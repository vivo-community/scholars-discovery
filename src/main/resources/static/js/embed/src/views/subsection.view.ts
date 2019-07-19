import { environment } from '../environments/environment.ts';

export class View {
    public id: string;
    public name: string;
    public markup: string;
    public length: number;

    constructor(id?: string, name?: string, markup?: string, length?: number) {
        this.id = id ? id : '';
        this.name = name ? name : '';
        this.markup = markup ? markup : '';
        this.length = length ? length : 0;
    };

    public render = (): string => {
        if (this.length == 0) {
            return '';
        }

        return '<div class="subsection mb-3">' +
               '  <div class="subsection-header font-weight-bold">' +
               '    <span class="subsection-name text-capitalize">' + this.name + '</span>' +
               '    <span class="subsection-length badge badge-light ml-2">' + this.length + '</span>' +
               '  </div>' +
               '  <div class="subsection-block list-group" data-id="' + this.id + '">' +  this.markup + '</div>' +
               '</div>';
    };
}
