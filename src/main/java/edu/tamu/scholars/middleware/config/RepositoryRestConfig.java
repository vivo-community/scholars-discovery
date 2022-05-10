package edu.tamu.scholars.middleware.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

import edu.tamu.scholars.middleware.auth.model.repo.handler.UserEventHandler;
import edu.tamu.scholars.middleware.theme.model.repo.handler.ThemeEventHandler;

@Configuration
public class RepositoryRestConfig implements RepositoryRestConfigurer {

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
