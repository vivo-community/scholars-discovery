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
        const range: number = environment.pagination.range as number;
        var markup: string = '';

        if (this.list.length == 0) {
            return markup;
        }

        if (this.list.length <= limit) {
            for (let i in this.list) {
                markup += '<li class="paginated-visible list-group-item border-0">' + this.list[i] + '</li>';
            }
            return markup;
        }

        var page: number = 1;
        var pages: number = Math.ceil(this.list.length / limit);

        for (let i = 0; i < this.list.length; i++) {
            page = Math.floor(i / limit) + 1;

            if (i >= limit) {
                markup += '<li class="paginated-hidden list-group-item border-0" data-page="' + page + '">';
            }
            else {
                markup += '<li class="paginated-visible list-group-item border-0" data-page="' + page + '">';
            }

            markup += this.list[i] + '</li>';
        }

        markup += '<nav aria-label="' + this.label + '" class="pagination-controls form-group mt-3">' +
                  '  <ul class="pagination" data-pagination="' + this.id + '" data-pages="' + pages + '" data-range="' + range + '">' +
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
                  '    </li>' +
                  '    <li class="page-item item-1 active">' +
                  '      <a class="page-link" href="#" data-page="1">1</a>' +
                  '    </li>' +
                  '    <li class="page-item dots-first disabled hidden">' +
                  '      <a class="page-link" href="#">...</a>' +
                  '    </li>';

        if (pages > 1) {
            for (let i = 1; i < pages && i < range; i++) {
                let page: string = (i + 1).toString();
                markup += '    <li class="page-item item-' + page + '"><a class="page-link" href="#" data-page="' + page + '">' + page + ' </a></li>';
            }
        }

        if (pages >= range) {
            markup += '    <li class="page-item item-' + (range + 1) + ' disabled hidden">' +
                      '      <a class="page-link" href="#" data-page=""></a>' +
                      '    </li>' +
                      '    <li class="page-item dots-last disabled"><a class="page-link" href="#">...</a></li>' +
                      '    <li class="page-item item-' + (range + 2) + '">' +
                      '      <a class="page-link" href="#" data-page="' + pages + '">' + pages + '</a>' +
                      '    </li>';
        }
        else {
            markup += '    <li class="page-item dots-last disabled hidden"><a class="page-link" href="#">...</a></li>';
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
                  '  <div class="page-control-divider"></div>' +
                  '</nav>';

        return markup;
    };
}
