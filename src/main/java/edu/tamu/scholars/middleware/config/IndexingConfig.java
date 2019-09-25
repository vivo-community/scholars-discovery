package edu.tamu.scholars.middleware.config;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import edu.tamu.scholars.middleware.config.model.IndexerConfig;
import edu.tamu.scholars.middleware.config.model.MiddlewareConfig;
import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.discovery.service.Indexer;

@Configuration
@Profile("!test")
public class IndexingConfig {

    @Autowired
    private MiddlewareConfig middleware;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Bean
    public List<Indexer> indexers() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        List<Indexer> indexers = new ArrayList<Indexer>();
        for (IndexerConfig config : middleware.getIndexers()) {
            for (Class<? extends AbstractIndexDocument> documentType : config.getDocumentTypes()) {
                indexers.add(indexed(config.getType(), documentType));
            }
        }
        return indexers;
    }

    public Indexer indexed(Class<? extends Indexer> type, Class<? extends AbstractIndexDocument> documentType) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Indexer indexer = type.getConstructor(Class.class).newInstance(documentType);
        beanFactory.autowireBean(indexer);
        return indexer;
    }

}
