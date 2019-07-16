Handlebars.registerHelper("formalize", function (value) {
    if (Array.isArray(value)) {
        var formalValues = [];
        for (var entry in environment.formalize) {
            formalValues.push(formalize(entry));
        }

        return formalValues;
    }

    var replacements = {
        otherUniversity: 'ExternalOrganization',
        GreyLiterature: 'InstitutionalRepositoryDocument'
    };

    for (var key in replacements) {
        if (replacements.hasOwnProperty(key) && value === key) {
            value = replacements[key];
        }
    }

    //value = value.replace(/([A-Z])/g, ' $1').replace(/^./, (str) => str.toUpperCase());
    value = value.replace(/([A-Z])/g, ' $1');
    return value;
});

Handlebars.registerHelper("ifEquals", function (arg1, arg2, options) {
    return (arg1 === arg2) ? options.fn(this) : options.inverse(this);
});

Handlebars.registerHelper("toYear", function (value) {
    return value !== undefined ? new Date(value).getFullYear() : value;
});

Handlebars.registerHelper("toDate", function (value) {
    return value !== undefined ? new Date(value).toISOString() : value;
});

$('.scholars-embed').each(function() {
    var id = $(this).data('id');
    var displayView = $(this).data('displayview');
    var sections = $(this).data('sections').split(',');
    var $embed = $(this);

    $.ajax('http://localhost:9000/displayViews/search/findByName?name=' + displayView).then(function(templates) {
        var source = '';

        $.ajax('http://localhost:9000/persons/' + id).then(function(data) {
            $.each(sections, function(key, value) {
                $.each(templates.tabs, function(i, tab) {
                    $.each(tab.sections, function(j, section) {
                        if (section.name === value) {
                            source += section.template;

                            $.each(section.subsections, function(k, subsection) {
                                if (subsection.hasOwnProperty('template') && subsection.hasOwnProperty('field') && data.hasOwnProperty(subsection.field)) {
                                    var subtemplate = Handlebars.compile(subsection.template);
                                    for (var fieldIndex in data[subsection.field]) {
                                        source += subtemplate(data[subsection.field][fieldIndex]);
                                    }
                                }
                            });

                            return false;
                        }
                    });
                });
            });

            var template = Handlebars.compile(source);
            var context = data;
            var html    = template(context);
            $embed.html(html);
        });
    });
});
