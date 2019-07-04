package edu.tamu.scholars.middleware.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import edu.tamu.scholars.middleware.auth.model.repo.handler.UserEventHandler;
import edu.tamu.scholars.middleware.discovery.resolver.ExportArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.FacetArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.FilterArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.IndexArgumentResolver;
import edu.tamu.scholars.middleware.theme.model.repo.handler.ThemeEventHandler;

@Configuration
public class RepositoryRestConfig extends RepositoryRestMvcConfiguration {

    public RepositoryRestConfig(ApplicationContext context, ObjectFactory<ConversionService> conversionService) {
        super(context, conversionService);
    }

    @Override
    @ConfigurationProperties(prefix = "spring.data.rest")
    public RepositoryRestConfiguration repositoryRestConfiguration() {
        return super.repositoryRestConfiguration();
    }

    @Override
    protected List<HandlerMethodArgumentResolver> defaultMethodArgumentResolvers() {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>(super.defaultMethodArgumentResolvers());
        resolvers.add(new IndexArgumentResolver());
        resolvers.add(new FilterArgumentResolver());
        resolvers.add(new FacetArgumentResolver());
        resolvers.add(new ExportArgumentResolver());
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
