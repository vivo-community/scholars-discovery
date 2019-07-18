import { environment } from '../environments/environment.ts';

export class View {
    public id: string;
    public label: string;
    public list: string[];

    constructor(id?: string, label?: string, list?: string[]) {
        this.id = id ? id : '';
        this.label = label ? label : '';
        this.list = list ? list : [];
    };

    public render = (): string => {
        const limit: number = environment.pagination.limit as number;
        var markup: string = '';

        if (this.list.length == 0) {
            return markup;
        }

        if (this.list.length <= limit) {
            for (let i in this.list) {
                markup += this.list[i];
            }
            return markup;
        }

        var total: number = Math.ceil(this.list.length / limit);
        var page: number = 1;

        for (let i = 0; i < this.list.length; i++) {
            page = Math.floor(i / limit) + 1;

            if (i >= limit) {
                markup += '<div class="paginated paginated-hidden list-group-item border-0" data-id="' + this.id + '" data-page="' + page + '">';
            }
            else {
                markup += '<div class="paginated paginated-visible list-group-item border-0" data-id="' + this.id + '" data-page="' + page + '">';
            }

            markup += this.list[i] + '</div>';
        }

        markup += '<nav aria-label="' + this.label + '" class="pagination-controls form-group mt-3" data-pagination="' + this.id + '">' +
                  '  <ul class="pagination" data-pagination="' + this.id + '">' +
                  '    <li class="page-item disabled">' +
                  '      <a class="page-link" href="#" aria-label="First" data-page="first">' +
                  '        <span aria-hidden="true">&laquo;&laquo;</span>' +
                  '        <span class="sr-only">First</span>' +
                  '      </a>' +
                  '    </li>' +
                  '    <li class="page-item disabled">' +
                  '      <a class="page-link" href="#" aria-label="Previous" data-page="previous">' +
                  '        <span aria-hidden="true">&laquo;</span>' +
                  '        <span class="sr-only">Previous</span>' +
                  '      </a>' +
                  '    </li>';

        for (let i = 0; i < total && i < environment.pagination.display; i++) {
            let page: string = (i + 1).toString();
            markup += '    <li class="page-item' + (i == 0 ? ' active' : '') + '"><a class="page-link" href="#" data-page="' + page + '">' + page + ' </a></li>';
        }

        markup += '    <li class="page-item">' +
                  '      <a class="page-link" href="#" aria-label="Next" data-page="next">' +
                  '        <span aria-hidden="true">&raquo;</span>' +
                  '        <span class="sr-only">Next</span>' +
                  '      </a>' +
                  '    </li>' +
                  '    <li class="page-item">' +
                  '      <a class="page-link" href="#" aria-label="Last" data-page="last">' +
                  '        <span aria-hidden="true">&raquo;&raquo;</span>' +
                  '        <span class="sr-only">Last</span>' +
                  '      </a>' +
                  '    </li>' +
                  '  </ul>' +
                  '</nav>';

        return markup;
    };
}
