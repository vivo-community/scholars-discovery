import {renderTemplate, initializeTemplateHelpers} from './utilities/template.utility';

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
//                                    var subtemplate = renderTemplate(subsection.template);
                                    for (var fieldIndex in data[subsection.field]) {
                                        source += renderTemplate(subsection.template, data[subsection.field][fieldIndex]);
                                    }
                                }
                            });

                            return false;
                        }
                    });
                });
            });

            //var template = renderTemplate(source);
            var context = data;
            var html    = renderTemplate(source, context);;
            $embed.html(html);
        });
    });
});