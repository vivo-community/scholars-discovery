$(document).ready(function() {
    $('.page-item .page-link').click(function(e) {
        e.preventDefault();

        var $link = $(this);
        var $item = $link.parent();

        if ($item.hasClass('disabled') || $item.hasClass('active')) {
            return;
        }

        var $pagination = $item.parent();
        var $minus_one;
        var $plus_one;

        var page = $link.attr('data-page');
        var pages = Number($pagination.attr('data-pages'));
        var paginationId = $pagination.attr('data-pagination');
        var current;

        if (page === 'first' || page == 'previous' || page === 'next' || page === 'last') {
            current = Number($pagination.find('.page-item.active .page-link').first().attr('data-page'));
        }
        else {
            current = Number($link.attr('data-page'));
        }

        if (isNaN(current)) {
            current = 0;
        }

        if (isNaN(pages)) {
            pages = 0;
        }

        if (pages < 2 || current > pages || current < 1) {
            console.error('Pagination current = ' + current + ' and pages = ' + pages + ' for pagination "' + paginationId + '".');
            return;
        }

        if (current > 0) {
          $minus_one = $pagination.find('.page-item .page-link[data-page="' + (current - 1) + '"]').first();
        }

        if (current < pages) {
          $plus_one = $pagination.find('.page-item .page-link[data-page="' + (current + 1) + '"]').first();
        }

        $pagination.children('.pagination-controls .pagination .page-item.active').each(function() {
            var $active=$(this);
            $active.removeClass('active');
        });

        $pagination.children('.pagination-controls .pagination .page-item.disabled').each(function() {
            var $disabled=$(this);
            $disabled.removeClass('disabled');
        });

        var $active;
        var $first = $pagination.find('.page-item .page-link[data-page="first"]').first().parent();
        var $previous = $pagination.find('.page-item .page-link[data-page="previous"]').first().parent();
        var $next = $pagination.find('.page-item .page-link[data-page="next"]').first().parent();
        var $last = $pagination.find('.page-item .page-link[data-page="last"]').last().parent();

        if (page === 'first') {
            $first.addClass('disabled');
            $previous.addClass('disabled');

            $active =  $pagination.find('.page-item .page-link[data-page="1"]').first().parent();
            $active.addClass('active');

            current = 1;
        }
        else if (page === 'previous') {
            if (current == 2) {
                $first.addClass('disabled');
                $previous.addClass('disabled');
            }

            $active =  $minus_one.parent();
            $active.addClass('active');

            current--;
        }
        else if (page === 'next') {
            if (current == pages - 1) {
                $next.addClass('disabled');
                $last.addClass('disabled');
            }

            $active =  $plus_one.parent();
            $active.addClass('active');

            current++;
        }
        else if (page === 'last') {
            $next.addClass('disabled');
            $last.addClass('disabled');

            $active =  $pagination.find('.page-item .page-link[data-page="' + pages + '"]').first().parent();
            $active.addClass('active');

            current = pages;
        }
        else {
            if (current == 1) {
                $first.addClass('disabled');
                $previous.addClass('disabled');
            }
            else if (current == pages) {
                $next.addClass('disabled');
                $last.addClass('disabled');
            }

            $active =  $link.parent();
            $active.addClass('active');
        }

        $subsection = $pagination.parent().parent();

        $subsection.children('.paginated-visible').each(function() {
            var $field = $(this);
            $field.removeClass('paginated-visible');
            $field.addClass('paginated-hidden');
        });

        $subsection.children('.paginated-hidden[data-page="' + current + '"]').each(function() {
            var $field = $(this);
            $field.removeClass('paginated-hidden');
            $field.addClass('paginated-visible');
        });
    });
});
