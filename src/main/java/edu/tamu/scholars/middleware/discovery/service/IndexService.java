package edu.tamu.scholars.middleware.discovery.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import edu.tamu.scholars.middleware.service.Triplestore;

@Service
public class IndexService {

    private final static Logger logger = LoggerFactory.getLogger(IndexService.class);

    private final static AtomicBoolean indexing = new AtomicBoolean(false);

    @Value("${middleware.index.onStartup:false}")
    public boolean indexOnStartup;

    @Value("${middleware.index.onStartupDelay:10000}")
    private int indexOnStartupDelay;

    @Value("${middleware.index.batchSize:10000}")
    private int indexBatchSize;

    @Autowired
    private List<Harvester> harvesters;

    @Autowired
    private List<Indexer> indexers;

    @Autowired
    private Triplestore triplestore;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @PostConstruct
    public void indexOnStartup() {
        if (indexOnStartup) {
            threadPoolTaskScheduler.execute(new Runnable() {

                @Override
                public void run() {
                    index();
                }

            }, indexOnStartupDelay);
        }
    }

    @Scheduled(cron = "${middleware.index.cron}", zone = "${middleware.index.zone}")
    public void index() {
        if (indexing.compareAndSet(false, true)) {
            triplestore.init();
            Instant start = Instant.now();
            logger.info("Indexing...");
            harvesters.parallelStream().forEach(harvester -> {
                logger.info(String.format("Indexing %s documents.", harvester.type().getSimpleName()));
                if (indexers.stream().anyMatch(indexer -> indexer.type().equals(harvester.type()))) {
                    harvester.harvest().buffer(indexBatchSize).subscribe(batch -> {
                        indexers.parallelStream().filter(indexer -> indexer.type().equals(harvester.type())).forEach(indexer -> {
                            indexer.index(batch);
                        });
                    });
                } else {
                    logger.warn(String.format("No indexer found for %s documents!", harvester.type().getSimpleName()));
                }
                logger.info(String.format("Indexing %s documents finished.", harvester.type().getSimpleName()));
            });
            logger.info(String.format("Indexing finished. %s seconds.", Duration.between(start, Instant.now()).toMillis() / 1000.0));
            triplestore.destroy();
            indexing.set(false);
        } else {
            logger.info("Already indexing. Waiting for next schedule.");
        }
    }

}
