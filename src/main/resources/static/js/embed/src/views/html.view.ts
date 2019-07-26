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
               '  <script type="application/javascript">' +
               '    $(document).ready(function() {' +
               '        $(\'.page-item .page-link\').click(function(e) {' +
               '            e.preventDefault();' +
               '    ' +
               '            var $link = $(this);' +
               '            var $item = $link.parent();' +
               '    ' +
               '            if ($item.hasClass(\'disabled\') || $item.hasClass(\'active\')) {' +
               '                return;' +
               '            }' +
               '    ' +
               '            var $pagination = $item.parent();' +
               '            var $minus_one;' +
               '            var $plus_one;' +
               '    ' +
               '            var page = $link.attr(\'data-page\');' +
               '            var pages = Number($pagination.attr(\'data-pages\'));' +
               '            var paginationId = $pagination.attr(\'data-pagination\');' +
               '            var range = Number($pagination.attr(\'data-range\'));' +
               '            var current;' +
               '    ' +
               '            if (page === \'first\' || page == \'previous\' || page === \'next\' || page === \'last\') {' +
               '                current = Number($pagination.find(\'.page-item.active .page-link\').first().attr(\'data-page\'));' +
               '            }' +
               '            else {' +
               '                current = Number($link.attr(\'data-page\'));' +
               '            }' +
               '    ' +
               '            if (isNaN(current)) {' +
               '                current = 0;' +
               '            }' +
               '    ' +
               '            if (isNaN(pages)) {' +
               '                pages = 0;' +
               '            }' +
               '    ' +
               '            if (isNaN(range)) {' +
               '                range = 5;' +
               '            }' +
               '    ' +
               '            if (pages < 2 || current > pages || current < 1) {' +
               '                console.error(\'Pagination current = \' + current + \' and pages = \' + pages + \' for pagination "\' + paginationId + \'".\');' +
               '                return;' +
               '            }' +
               '    ' +
               '            var showItem = function($item) {' +
               '                if ($item.hasClass(\'hidden\')) {' +
               '                    $item.removeClass(\'hidden\');' +
               '                }' +
               '            };' +
               '    ' +
               '            var hideItem = function($item) {' +
               '                if (!$item.hasClass(\'hidden\')) {' +
               '                    $item.addClass(\'hidden\');' +
               '                }' +
               '            };' +
               '    ' +
               '            var enableItem = function($item) {' +
               '                if ($item.hasClass(\'disabled\')) {' +
               '                    $item.removeClass(\'disabled\');' +
               '                }' +
               '            };' +
               '    ' +
               '            var disableItem = function($item) {' +
               '                if (!$item.hasClass(\'disabled\')) {' +
               '                    $item.addClass(\'disabled\');' +
               '                }' +
               '            };' +
               '    ' +
               '            if (current > 0) {' +
               '              $minus_one = $pagination.find(\'.page-item .page-link[data-page="\' + (current - 1) + \'"]\').first();' +
               '            }' +
               '    ' +
               '            if (current < pages) {' +
               '              $plus_one = $pagination.find(\'.page-item .page-link[data-page="\' + (current + 1) + \'"]\').first();' +
               '            }' +
               '    ' +
               '            $pagination.children(\'.pagination-controls .pagination .page-item.active\').each(function() {' +
               '                var $active=$(this);' +
               '    ' +
               '                if ($active.children(\'.page-link\').first().is(\'[data-page]\')) {' +
               '                    $active.removeClass(\'active\');' +
               '                }' +
               '            });' +
               '    ' +
               '            $pagination.children(\'.pagination-controls .pagination .page-item.disabled\').each(function() {' +
               '                var $disabled=$(this);' +
               '    ' +
               '                if ($disabled.children(\'.page-link\').first().is(\'[data-page]\')) {' +
               '                    $disabled.removeClass(\'disabled\');' +
               '                }' +
               '            });' +
               '    ' +
               '            var $active;' +
               '            var $first = $pagination.find(\'.page-item .page-link[data-page="first"]\').first().parent();' +
               '            var $previous = $pagination.find(\'.page-item .page-link[data-page="previous"]\').first().parent();' +
               '            var $next = $pagination.find(\'.page-item .page-link[data-page="next"]\').first().parent();' +
               '            var $last = $pagination.find(\'.page-item .page-link[data-page="last"]\').last().parent();' +
               '    ' +
               '            if (page === \'first\') {' +
               '                disableItem($first);' +
               '                disableItem($previous);' +
               '    ' +
               '                current = 1;' +
               '            }' +
               '            else if (page === \'previous\') {' +
               '                if (current == 2) {' +
               '                    disableItem($first);' +
               '                    disableItem($previous);' +
               '                }' +
               '    ' +
               '                current--;' +
               '            }' +
               '            else if (page === \'next\') {' +
               '                if (current == pages - 1) {' +
               '                    disableItem($next);' +
               '                    disableItem($last);' +
               '                }' +
               '    ' +
               '                current++;' +
               '            }' +
               '            else if (page === \'last\') {' +
               '                disableItem($next);' +
               '                disableItem($last);' +
               '    ' +
               '                current = pages;' +
               '            }' +
               '            else {' +
               '                if (current == 1) {' +
               '                    disableItem($first);' +
               '                    disableItem($previous);' +
               '                }' +
               '                else if (current == pages) {' +
               '                    disableItem($next);' +
               '                    disableItem($last);' +
               '                }' +
               '            }' +
               '    ' +
               '            var $dotsFirst = $pagination.find(\'.page-item.dots-first\').first();' +
               '            var $dotsLast = $pagination.find(\'.page-item.dots-last\').first();' +
               '            var $itemExtra = $pagination.find(\'.page-item.item-\' + (range + 1)).first();' +
               '            var newPage = 0;' +
               '            var position;' +
               '            var $position;' +
               '            var $positionLink;' +
               '    ' +
               '            if (current < range) {' +
               '                hideItem($dotsFirst);' +
               '            }' +
               '            else {' +
               '                showItem($dotsFirst);' +
               '            }' +
               '    ' +
               '            if (current < range) {' +
               '                hideItem($dotsFirst);' +
               '                showItem($dotsLast);' +
               '    ' +
               '                $active = $pagination.find(\'.page-item.item-\' + current).first();' +
               '                $active.addClass(\'active\');' +
               '    ' +
               '                newPage = 2;' +
               '                for (position = 2; position <= range; position++, newPage++) {' +
               '                    $position = $pagination.find(\'.page-item.item-\' + position).first();' +
               '                    $positionLink = $position.children(\'.page-link\').first();' +
               '                    $positionLink.attr(\'data-page\', newPage);' +
               '                    $positionLink.text(newPage);' +
               '                }' +
               '    ' +
               '                $positionLink = $itemExtra.children(\'.page-link\').first();' +
               '                if (current < range - 1) {' +
               '                    $positionLink.attr(\'data-page\', \'\');' +
               '                    $positionLink.text(\'\');' +
               '    ' +
               '                    hideItem($itemExtra);' +
               '                    disableItem($itemExtra);' +
               '                }' +
               '                else {' +
               '                    $positionLink.attr(\'data-page\', newPage);' +
               '                    $positionLink.text(newPage);' +
               '    ' +
               '                    showItem($itemExtra);' +
               '                    enableItem($itemExtra);' +
               '                }' +
               '            }' +
               '            else if (current >= range && current <= pages - (range - 1)) {' +
               '                showItem($dotsFirst);' +
               '                showItem($dotsLast);' +
               '    ' +
               '                showItem($itemExtra);' +
               '                enableItem($itemExtra);' +
               '    ' +
               '                position = 4;' +
               '                newPage = current - 2;' +
               '    ' +
               '                $active = $pagination.find(\'.page-item.item-\' + position).first();' +
               '                $active.addClass(\'active\');' +
               '    ' +
               '                for (position = 2; position <= range + 1; position++, newPage++) {' +
               '                    $position = $pagination.find(\'.page-item.item-\' + position).first();' +
               '                    $positionLink = $position.children(\'.page-link\').first();' +
               '                    $positionLink.attr(\'data-page\', newPage);' +
               '                    $positionLink.text(newPage);' +
               '                }' +
               '            }' +
               '            else {' +
               '                showItem($dotsFirst);' +
               '                hideItem($dotsLast);' +
               '    ' +
               '                newPage = (pages - range) + 1;' +
               '                if (current == newPage + 1) {' +
               '                    newPage--;' +
               '                    position = (range - (pages - current)) + 2;' +
               '                }' +
               '                else if (current == pages) {' +
               '                    position = 7;' +
               '                }' +
               '                else {' +
               '                    position = (range - (pages - current)) + 1;' +
               '                }' +
               '    ' +
               '                $active = $pagination.find(\'.page-item.item-\' + position).first();' +
               '                $active.addClass(\'active\');' +
               '    ' +
               '                for (position = 2; position <= range; position++, newPage++) {' +
               '                    $position = $pagination.find(\'.page-item.item-\' + position).first();' +
               '                    $positionLink = $position.children(\'.page-link\').first();' +
               '                    $positionLink.attr(\'data-page\', newPage);' +
               '                    $positionLink.text(newPage);' +
               '                }' +
               '    ' +
               '                $positionLink = $itemExtra.children(\'.page-link\').first();' +
               '                if (current > (pages - range) + 2) {' +
               '                    $positionLink.attr(\'data-page\', \'\');' +
               '                    $positionLink.text(\'\');' +
               '    ' +
               '                    hideItem($itemExtra);' +
               '                    disableItem($itemExtra);' +
               '                }' +
               '                else {' +
               '                    $positionLink.attr(\'data-page\', newPage);' +
               '                    $positionLink.text(newPage);' +
               '    ' +
               '                    showItem($itemExtra);' +
               '                    enableItem($itemExtra);' +
               '                }' +
               '            }' +
               '    ' +
               '            $subsection = $pagination.parent().parent();' +
               '    ' +
               '            $subsection.children(\'.paginated-visible\').each(function() {' +
               '                var $field = $(this);' +
               '                $field.removeClass(\'paginated-visible\');' +
               '                $field.addClass(\'paginated-hidden\');' +
               '            });' +
               '    ' +
               '            $subsection.children(\'.paginated-hidden[data-page="\' + current + \'"]\').each(function() {' +
               '                var $field = $(this);' +
               '                $field.removeClass(\'paginated-hidden\');' +
               '                $field.addClass(\'paginated-visible\');' +
               '            });' +
               '        });' +
               '    });' +
               '  </script>' +
               '  <style type="text/css">' +
               '    :root {' +
               '      --primary: #500000;--link-color: #2b5d7d;' +
               '    }' +
               '    body a { color: var(--link-color); }' +
               '    .text-primary {color: var(--primary) !important;}' +
               '    .subsection-header .subsection-name { font-size: 1.25rem; }' +
               '    .pagination-controls { display: flex; }' +
               '    .pagination-controls .page-control-divider { flex-grow: 1; }' +
               '    .paginated-visible { }' +
               '    .paginated-hidden { display: none; }' +
               '    .page-item.hidden { display: none; }' +
               '    .list-group-item: { position: relative; display: block; padding: 0.75rem 1.25rem; margin-bottom: -1px; background-color: #ffffff; border: 1px solid #00000020; }' +
               '    .list-group-item:first-child { border-top-left-radius: 0.25rem; border-top-right-radius: 0.25rem; }' +
               '    .list-group-item:last-child { margin-bottom: 0; border-bottom-right-radius: 0.25rem; border-bottom-left-radius: 0.25rem; }' +
               '  </style>' +
               '</head>' +
               '<body>' + this.markup + '</body>';
    };
}
