import { environment } from '../environments/environment.ts';

import * as Pagination from './pagination.view';

export class View {
    public name: string;
    public markup: string;
    public length: number;

    constructor(name?: string, markup?: string, length?: number) {
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
               '  <div class="subsection-block list-group">' +  this.markup + '</div>' +
               '</div>';
    };
}
