$(document).ready(function() {
    $('.page-item .page-link').click(function(e) {
        e.preventDefault();

        var $link = $(this);
        var $item = $link.parent();

        if ($item.hasClass('disabled') || $item.hasClass('active')) {
            return;
        }

        var $pagination = $item.parent();
        var $first = $pagination.children('').first();

        var page = $link.attr('data-page');
        var paginationId = $pagination.attr('data-pagination');

        // TODO: logic needs to be completed.
        $pagination.children('.pagination-controls > .pagination > .page-item.active').each(function() {
            var $active=$(this);
            $active.removeClass('active');
        });

        $pagination.children('.pagination-controls > .pagination > .page-item.disabled').each(function() {
            var $disabled=$(this);
            $disabled.removeClass('active');
        });

        if (page === 'first') {
            // TODO
        }
        else if (page === 'previous') {
            // TODO
        }
        else if (page === 'next') {
            // TODO
        }
        else if (page === 'last') {
            // TODO
        }
        else {
            // TODO
        }

        // TODO
    });
});
