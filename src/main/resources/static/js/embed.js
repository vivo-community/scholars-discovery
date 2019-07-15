$(".scholars-embed").each(function() {
	var id = $(this).data("id");
	var displayView = $(this).data("displayview");
	var sections = $(this).data("sections").split(",");
	var $embed = $(this);

	$.ajax("http://localhost:9000/displayViews/search/findByName?name="+displayView).then(function(templates) {
		var source = "";
		$.ajax("http://localhost:9000/persons/"+id).then(function(data) {
			$.each(sections, function(key, value) {
				$.each(templates.tabs, function(i, tab) {
					$.each(tab.sections, function(j,section) {
						if (section.name === value) {
							source += section.template;
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