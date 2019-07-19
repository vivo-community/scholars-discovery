export const environment = {
    middleware: {
        baseUrl: 'http://localhost:9000',
        displayViewByType: '/displayViews/search/findByTypesIn?types=',
        displayViewByName: '/displayViews/search/findByName?name='
    },
    vivoUrl: 'https://duraspace.org/vivo',
    ngAppUrl: 'http://localhost:4200',
    formalize: {
        otherUniversity: 'ExternalOrganization',
        GreyLiterature: 'InstitutionalRepositoryDocument'
    },
    javascript: {
        jquery: 'https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js',
        pagination: 'http://localhost:9000/js/embed/dist/pagination.js'
    },
    stylesheets: {
        bootstrap: 'https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'
    },
    pagination: {
        limit: 5,
        display: 3,
        placeholder: 'pagination-disabled-'
    }
};
