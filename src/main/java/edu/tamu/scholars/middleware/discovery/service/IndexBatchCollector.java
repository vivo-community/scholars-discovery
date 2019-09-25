package edu.tamu.scholars.middleware.discovery.service;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import edu.tamu.scholars.middleware.discovery.model.AbstractIndexDocument;

public class IndexBatchCollector implements Collector<AbstractIndexDocument, List<AbstractIndexDocument>, List<AbstractIndexDocument>> {

    private final Consumer<List<AbstractIndexDocument>> batchProcessor;

    private final int batchSize;

    public IndexBatchCollector(Consumer<List<AbstractIndexDocument>> batchProcessor, int batchSize) {
        this.batchProcessor = requireNonNull(batchProcessor);
        this.batchSize = batchSize;
    }

    public Supplier<List<AbstractIndexDocument>> supplier() {
        return ArrayList::new;
    }

    public BiConsumer<List<AbstractIndexDocument>, AbstractIndexDocument> accumulator() {
        return (ts, t) -> {
            ts.add(t);
            if (ts.size() >= batchSize) {
                batchProcessor.accept(ts);
                ts.clear();
            }
        };
    }

    public BinaryOperator<List<AbstractIndexDocument>> combiner() {
        return (ts, ots) -> {
            batchProcessor.accept(ts);
            batchProcessor.accept(ots);
            return Collections.emptyList();
        };
    }

    public Function<List<AbstractIndexDocument>, List<AbstractIndexDocument>> finisher() {
        return ts -> {
            batchProcessor.accept(ts);
            return Collections.emptyList();
        };
    }

    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }

    public static IndexBatchCollector of(Consumer<List<AbstractIndexDocument>> batchProcessor, int batchSize) {
        return new IndexBatchCollector(batchProcessor, batchSize);
    }

}