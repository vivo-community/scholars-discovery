package edu.tamu.scholars.middleware.discovery.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;
import edu.tamu.scholars.middleware.service.Triplestore;

@Service
@Profile("!test")
public class IndexService {

    private final static Logger logger = LoggerFactory.getLogger(IndexService.class);

    private final static AtomicBoolean indexing = new AtomicBoolean(false);

    @Value("${middleware.index.onStartup:false}")
    public boolean indexOnStartup;

    @Value("${middleware.index.batchSize:10000}")
    private int indexBatchSize;

    @Autowired
    private List<Harvester> harvesters;

    @Autowired
    private List<Indexer> indexers;

    @Autowired
    private Triplestore triplestore;

    @PostConstruct
    public void indexOnStartup() {
        if (indexOnStartup) {
            index();
        }
    }

    @Scheduled(cron = "${middleware.index.cron}", zone = "${middleware.index.zone}")
    public void index() {
        if (indexing.compareAndSet(false, true)) {
            triplestore.init();
            Instant start = Instant.now();
            logger.info("Indexing...");
            harvesters.parallelStream().forEach(harvester -> {
                logger.info(String.format("Indexing %s documents", harvester.type().getSimpleName()));
                indexers.parallelStream().filter(indexer -> indexer.type().equals(harvester.type())).forEach(indexer -> {
                    List<AbstractIndexDocument> batch = new ArrayList<AbstractIndexDocument>();
                    Stream<AbstractIndexDocument> stream = harvester.harvest();
                    stream.forEach(document -> {
                        batch.add(document);
                        if (batch.size() >= indexBatchSize) {
                            indexer.index(batch);
                            batch.clear();
                        }
                    });
                    if (!batch.isEmpty()) {
                        indexer.index(batch);
                    }
                    stream.close();
                });
                logger.info(String.format("Indexing %s documents finished.", harvester.type().getSimpleName()));
            });
            logger.info(String.format("Indexing finished. %s seconds", Duration.between(start, Instant.now()).toMillis() / 1000.0));
            triplestore.destroy();
            indexing.set(false);
        } else {
            logger.info("Already indexing. Waiting for next schedule.");
        }
    }

}
