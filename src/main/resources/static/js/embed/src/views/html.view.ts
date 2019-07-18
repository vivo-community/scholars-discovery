import { environment } from '../environments/environment.ts';

export class View {
    public markup: string;

    constructor(markup?: string) {
        this.markup = markup ? markup : '';
    };

    public render = (): string => {
        return '<head>' +
               '  <base href="' + environment.ngAppUrl + '">' +
               '  <link rel="stylesheet" href="' + environment.stylesheets.bootstrap + '" />' +
               '  <script type="application/javascript" src="' + environment.javascript.jquery + '"></script>' +
               '  <script type="application/javascript" src="' + environment.javascript.pagination + '"></script>' + // TODO: this is temporary.
               '  <style type="text/css">' +
               '    :root{' +
               '      --primary: #500000;--link-color: #2b5d7d;' +
               '    }' +
               '    body a {color: var(--link-color);}' +
               '    .text-primary {color: var(--primary) !important;}' +
               '    .subsection-header .subsection-name { font-size: 1.25rem; }' +
               '    .paginated-visible: { }' +
               '    .paginated-hidden: { display: none; }' +
               '  </style>' +
               '</head>' +
               '<body>' + this.markup + '</body>';
    };
}
