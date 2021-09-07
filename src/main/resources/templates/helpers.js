Handlebars.registerHelper('toYear', function (value) {
    if (value) {
        const date = new Date(value);
        value = Number(date.getUTCFullYear()).toString();
    }
    return value;
});

Handlebars.registerHelper('toFormalRole', function (value) {
    if (value) {
        switch (value) {
            case "PrincipalInvestigatorRole": return "Principal Investigator (PI)";
            case "CoPrincipalInvestigatorRole": return "Co-Principal Investigator (Co-PI)";
            case "InvestigatorRole": return "Investigator";
        }
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

Handlebars.registerHelper('eachSplit', function (value, delimeter, options) {
    let out = '';
    if (value) {
        value = value.toString();
        const parts = value.split(delimeter);
        for (const i in parts) {
            if (parts.hasOwnProperty(i)) {
                out += options.fn(parts[i].trim());
            }
        }
    }
    return out;
});

Handlebars.registerHelper('eachSorted', function (resources, field, direction, isDate, options) {
    let out = '';
    if (resources) {
        direction = (direction && direction.toLowerCase() === 'asc') ? [1, -1] : [-1, 1];
        resources = JSON.parse(resources).sort(function (r1, r2) {
            const v1 = isDate ? new Date(r1[field]) : r1[field];
            const v2 = isDate ? new Date(r2[field]) : r2[field];
            if (v1 > v2) {
                return direction[0];
            }
            if (v1 < v2) {
                return direction[1];
            }
            return 0;
        });
        for (const i in resources) {
            if (resources.hasOwnProperty(i)) {
                out += options.fn(resources[i]);
            }
        }
    }
    return out;
});

function filterCurrentFunding(resources) {
    if (!resources) {
        return [];
    }
    resources = JSON.parse(resources).sort(function (r1, r2) {
        const v1 = r1['label'];
        const v2 = r2['label'];
        if (v1 > v2) {
            return 1;
        }
        if (v1 < v2) {
            return -1;
        }
        return 0;
    });
    const now = new Date();
    return resources.sort(function (r1, r2) {
        const v1 = new Date(r1['startDate']);
        const v2 = new Date(r2['startDate']);
        if (v1 > v2) {
            return -1;
        }
        if (v1 < v2) {
            return 1;
        }
        return 0;
    }).filter(function (r) {
        const startDate = new Date(r['startDate']);
        const endDate = new Date(r['endDate']);
        return startDate <= now && endDate >= now;
    });
}

Handlebars.registerHelper('hasCurrentFunding', function (resources, options) {
    return filterCurrentFunding(resources).length > 0
        ? options.fn(this)
        : options.inverse(this);
});

Handlebars.registerHelper('eachCurrentFunding', function (resources, options) {
    resources = filterCurrentFunding(resources);
    let out = '';
    for (const i in resources) {
        if (resources.hasOwnProperty(i)) {
            out += options.fn(resources[i]);
        }
    }
    return out;
});
