package edu.tamu.scholars.middleware.config;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.tamu.scholars.middleware.config.model.HarvesterConfig;
import edu.tamu.scholars.middleware.config.model.MiddlewareConfig;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.service.Harvester;

@Configuration
public class HarvestingConfig {

    @Autowired
    private MiddlewareConfig middleware;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Bean
    public List<Harvester> harvesters() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        List<Harvester> harvesters = new ArrayList<Harvester>();
        for (HarvesterConfig config : middleware.getHarvesters()) {
            for (Class<? extends AbstractIndexDocument> documentType : config.getDocumentTypes()) {
                harvesters.add(harvester(config.getType(), documentType));
            }
        }
        return harvesters;
    }

    public Harvester harvester(Class<? extends Harvester> type, Class<? extends AbstractIndexDocument> documentType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Harvester harvester = type.getConstructor(Class.class).newInstance(documentType);
        beanFactory.autowireBean(harvester);
        return harvester;
    }

}
