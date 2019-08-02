Handlebars.registerHelper('toYear', function (value) { return value !== undefined ? new Date(value).getFullYear().toString() : value; });

Handlebars.registerHelper('parseAuthorList', function (value) {
	if (value !== undefined && typeof value === 'object') {
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
