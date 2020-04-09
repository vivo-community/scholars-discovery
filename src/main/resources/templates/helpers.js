Handlebars.registerHelper('toYear', function (value) {
    if (value) {
        const date = new Date(value);
        value = Number(date.getUTCFullYear()).toString();
    }
    return value;
});

Handlebars.registerHelper('parseAuthorList', function (value) {
    if (value) {
        value = value.toString();
        if (value.startsWith('["')) {
            value = value.substring(2);
        }
        if (value.endsWith('"]')) {
            value = value.substring(0, value.length - 2);
        }
        return value;
    }
    return value;
});