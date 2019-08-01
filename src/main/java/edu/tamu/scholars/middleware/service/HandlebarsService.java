package edu.tamu.scholars.middleware.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Jackson2Helper;
import com.github.jknack.handlebars.JsonNodeValueResolver;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.context.MethodValueResolver;

@Service
public class HandlebarsService {

    private final Handlebars handlebars;

    @Value("classpath:static/helpers.js")
    private Resource helpers;

    public HandlebarsService() {
        handlebars = new Handlebars();
        handlebars.registerHelper("json", Jackson2Helper.INSTANCE);

    }

    @PostConstruct
    public void init() throws IOException, Exception {
        handlebars.registerHelpers(helpers.getFile());
    }

    // TODO: cache compiled templates
    public String template(String template, Object data) throws IOException {
        // @formatter:off
        Context context = Context.newBuilder(data).resolver(
            JsonNodeValueResolver.INSTANCE,
            JavaBeanValueResolver.INSTANCE,
            FieldValueResolver.INSTANCE,
            MapValueResolver.INSTANCE,
            MethodValueResolver.INSTANCE
        ).build();
        // @formatter:on
        return handlebars.compileInline(template).apply(context);
    }

}
