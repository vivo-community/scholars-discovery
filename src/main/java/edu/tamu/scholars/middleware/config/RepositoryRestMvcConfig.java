package edu.tamu.scholars.middleware.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.rest.webmvc.json.MappingAwarePageableArgumentResolver;
import org.springframework.data.rest.webmvc.json.MappingAwareSortArgumentResolver;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import edu.tamu.scholars.middleware.auth.model.repo.handler.UserEventHandler;
import edu.tamu.scholars.middleware.discovery.resolver.BoostArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.FacetArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.FilterArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.HighlightArgumentResolver;
import edu.tamu.scholars.middleware.discovery.resolver.QueryArgumentResolver;
import edu.tamu.scholars.middleware.export.resolver.ExportArgumentResolver;
import edu.tamu.scholars.middleware.theme.model.repo.handler.ThemeEventHandler;

@Configuration
public class RepositoryRestMvcConfig extends RepositoryRestMvcConfiguration {

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    public RepositoryRestMvcConfig(ApplicationContext context, ObjectFactory<ConversionService> conversionService) {
        super(context, conversionService);
    }

    @Override
    public RequestMappingHandlerAdapter repositoryExporterHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = super.repositoryExporterHandlerAdapter();
        requestMappingHandlerAdapter.setAsyncRequestTimeout(900000);
        requestMappingHandlerAdapter.setTaskExecutor(taskExecutor);
        return requestMappingHandlerAdapter;
    }

    @Override
    @ConfigurationProperties(prefix = "spring.data.rest")
    public RepositoryRestConfiguration repositoryRestConfiguration() {
        return super.repositoryRestConfiguration();
    }

    @Override
    protected List<HandlerMethodArgumentResolver> defaultMethodArgumentResolvers() {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(pageableResolver());
        resolvers.add(sortResolver());
        resolvers.add(new QueryArgumentResolver());
        resolvers.add(new FilterArgumentResolver());
        resolvers.add(new FacetArgumentResolver());
        resolvers.add(new BoostArgumentResolver());
        resolvers.add(new HighlightArgumentResolver());
        resolvers.add(new ExportArgumentResolver());
        super.defaultMethodArgumentResolvers().forEach(resolver -> {
            if (!(resolver instanceof MappingAwarePageableArgumentResolver || resolver instanceof MappingAwareSortArgumentResolver)) {
                resolvers.add(resolver);
            }
        });
        return resolvers;
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return resolver -> resolver.setOneIndexedParameters(true);
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
