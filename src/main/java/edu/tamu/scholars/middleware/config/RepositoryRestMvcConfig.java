package edu.tamu.scholars.middleware.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import edu.tamu.scholars.middleware.auth.model.repo.handler.UserEventHandler;
import edu.tamu.scholars.middleware.discovery.resolver.ExporterArgumentResolver;
import edu.tamu.scholars.middleware.discovery.service.Exporter;
import edu.tamu.scholars.middleware.theme.model.repo.handler.ThemeEventHandler;

@Configuration
public class RepositoryRestMvcConfig extends RepositoryRestMvcConfiguration {

    @Autowired
    private List<Exporter> exporters;

    public RepositoryRestMvcConfig(ApplicationContext context, ObjectFactory<ConversionService> conversionService) {
        super(context, conversionService);
    }

    @Override
    protected List<HandlerMethodArgumentResolver> defaultMethodArgumentResolvers() {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>(super.defaultMethodArgumentResolvers());
        resolvers.add(new ExporterArgumentResolver(exporters));
        return resolvers;
    }

    @Bean
    public ThemeEventHandler themeEventHandler() {
        return new ThemeEventHandler();
    }

    @Bean
    public UserEventHandler userEventHandler() {
        return new UserEventHandler();
    }

}
