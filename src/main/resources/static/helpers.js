Handlebars.registerHelper('toYear', function (value) { return value !== undefined ? new Date(value).getFullYear() : value; });
