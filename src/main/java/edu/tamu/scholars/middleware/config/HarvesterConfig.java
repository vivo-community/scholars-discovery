package edu.tamu.scholars.middleware.config;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import edu.tamu.scholars.middleware.config.model.MiddlewareConfig;
import edu.tamu.scholars.middleware.config.model.TriplestoreConfig;
import edu.tamu.scholars.middleware.service.Triplestore;

@Configuration
@Profile("!test")
public class HarvesterConfig {

    @Autowired
    private MiddlewareConfig middleware;

    @Bean
    public Triplestore triplestore() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return middleware.getTriplestore().getType().getConstructor(TriplestoreConfig.class).newInstance(middleware.getTriplestore());
    }

}
