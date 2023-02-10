package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.CLASS;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.COLLECTION;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DEFAULT_QUERY;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.MOD_TIME;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.QUERY_DELIMETER;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.QUERY_TEMPLATE;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.REQUEST_PARAM_DELIMETER;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.TYPE;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.StreamingResponseCallback;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.DiscoveryNetworkDescriptor;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;
import edu.tamu.scholars.middleware.discovery.exception.SolrRequestException;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.IndexDocumentRepo;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryFacetAndHighlightPage;
import edu.tamu.scholars.middleware.discovery.response.DiscoveryNetwork;

// TODO: use message translation for exception responses
public class IndividualRepoImpl implements IndexDocumentRepo<Individual> {

    private static final Logger logger = LoggerFactory.getLogger(IndividualRepoImpl.class);

    private static final Pattern RANGE_PATTERN = Pattern.compile("^\\[(.*?) TO (.*?)\\]$");

    @Value("${spring.data.solr.parser:edismax}")
    private String defType;

    @Value("${spring.data.solr.operator:AND}")
    private String defaultOperator;

    @Lazy
    @Autowired
    private SolrClient solrClient;

    @Override
    public <S extends Individual> S save(S document) {
        try {
            solrClient.addBean(COLLECTION, document);
            solrClient.commit(COLLECTION);
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to save document", e);
        }
        return document;
    }

    @Override
    public void delete(Individual document) {
        deleteById(document.getId());
    }

    @Override
    public long count() {
        return count(DEFAULT_QUERY);
    }

    @Override
    public List<Individual> findAll() {
        SolrQueryBuilder builder = new SolrQueryBuilder()
            .withRows(Integer.MAX_VALUE);

        return findAll(builder.query());
    }

    @Override
    public List<Individual> findAll(Sort sort) {
        SolrQueryBuilder builder = new SolrQueryBuilder()
            .withRows(Integer.MAX_VALUE)
            .withSort(sort);

        return findAll(builder.query());
    }

    @Override
    public List<Individual> findAllById(Iterable<String> ids) {
        try {
            SolrDocumentList documents = solrClient.getById(COLLECTION, IterableUtils.toList(ids));

            return solrClient.getBinder()
                .getBeans(Individual.class, documents);
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to find documents from ids", e);
        }
    }

    @Override
    public <S extends Individual> Iterable<S> saveAll(Iterable<S> documents) {
        List<S> individuals = IterableUtils.toList(documents);
        try {
            solrClient.addBeans(COLLECTION, individuals);
            solrClient.commit(COLLECTION);
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to save documents", e);
        }
        return documents;
    }

    @Override
    public Page<Individual> findAll(Pageable pageable) {
        SolrQueryBuilder builder = new SolrQueryBuilder()
            .withPage(pageable);

        return findAll(builder.query(), pageable);
    }

    @Override
    public Optional<Individual> findById(String id) {
        return Optional.ofNullable(getById(id));
    }

    @Override
    public boolean existsById(String id) {
        return count(String.format(QUERY_TEMPLATE, ID, id)) == 1;
    }

    @Override
    public void deleteById(String id) {
        try {
            solrClient.deleteById(COLLECTION, id);
            solrClient.commit(COLLECTION);
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to delete document by id", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        try {
            solrClient.deleteById(COLLECTION, IterableUtils.toList((Iterable<String>) ids));
            solrClient.commit(COLLECTION);
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to delete documents for ids", e);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Individual> documents) {
        List<String> ids = IterableUtils.toList(documents).stream()
            .map(i -> i.getId())
            .collect(Collectors.toList());
        deleteAllById(ids);
    }

    @Override
    public void deleteAll() {
        try {
            solrClient.deleteByQuery(COLLECTION, DEFAULT_QUERY);
            solrClient.commit(COLLECTION);
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to delete all documents", e);
        }
    }

    @Override
    public List<Individual> findByType(String type) {
        FilterArg filter = FilterArg.of(TYPE, Optional.of(type), Optional.empty(), Optional.empty());
        SolrQueryBuilder builder = new SolrQueryBuilder()
            .withFilters(Arrays.asList(filter))
            .withRows(Integer.MAX_VALUE);

        return findAll(builder.query());
    }

    @Override
    public List<Individual> findByIdIn(List<String> ids) {
        return findAllById(ids);
    }

    @Override
    public long count(String query, List<FilterArg> filters) {
        SolrQueryBuilder builder = new SolrQueryBuilder(query)
            .withFilters(filters);

        return count(builder.query());
    }

    @Override
    public List<Individual> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters) {
        SolrQueryBuilder builder = new SolrQueryBuilder()
            .withFilters(filters)
            .withSort(Sort.by(Direction.DESC, MOD_TIME))
            .withRows(limit);

        return findAll(builder.query());
    }

    @Override
    public DiscoveryFacetAndHighlightPage<Individual> search(
        QueryArg query,
        List<FacetArg> facets,
        List<FilterArg> filters,
        List<BoostArg> boosts,
        HighlightArg highlight,
        Pageable page
    ) {
        SolrQueryBuilder builder = new SolrQueryBuilder()
            .withQuery(query)
            .withFacets(facets)
            .withFilters(filters)
            .withBoosts(boosts)
            .withHighlight(highlight)
            .withPage(page);

        try {
            QueryResponse response = solrClient.query(COLLECTION, builder.query());

            return DiscoveryFacetAndHighlightPage.from(response, page, facets, highlight, Individual.class);
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to search documents", e);
        }
    }

    @Override
    public CompletableFuture<Iterator<Individual>> export(QueryArg query, List<FilterArg> filters, List<BoostArg> boosts, Sort sort) {
        SolrQueryBuilder builder = new SolrQueryBuilder()
            .withQuery(query)
            .withFilters(filters)
            .withBoosts(boosts)
            .withSort(sort)
            .withRows(Integer.MAX_VALUE);

        CompletableFuture<Iterator<Individual>> future = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            try {
                solrClient.queryAndStreamResponse(COLLECTION, builder.query(), new StreamingResponseCallback() {
                    private final AtomicBoolean initialized = new AtomicBoolean(false);
                    private final AtomicBoolean streaming = new AtomicBoolean(false);
                    private final AtomicLong remaining = new AtomicLong(0);
                    private final BlockingQueue<Individual> queue = new LinkedBlockingQueue<>();

                    @Override
                    public void streamSolrDocument(SolrDocument doc) {
                        Individual individual = new Individual();

                        individual.setContent(doc.getFieldValuesMap());
                        individual.setId(doc.getFieldValue(ID).toString());
                        individual.setClazz(doc.getFieldValue(CLASS).toString());
                        individual.setType(doc.getFieldValues(TYPE).stream().map(to -> to.toString()).collect(Collectors.toList()));

                        queue.add(individual);

                        if (initialized.compareAndSet(false, true)) {
                            future.complete(new Iterator<Individual>() {

                                @Override
                                public boolean hasNext() {
                                    return streaming.get() || !queue.isEmpty();
                                }

                                @Override
                                public Individual next() {
                                    try {
                                        return queue.take();
                                    } catch (InterruptedException e) {
                                        throw new SolrRequestException("Failed to stream documents", e);
                                    }
                                }

                            });
                        }

                        if (remaining.decrementAndGet() <= 0) {
                            streaming.set(false);
                        }
                    }

                    @Override
                    public void streamDocListInfo(long numFound, long start, Float maxScore) {
                        streaming.set(numFound > 0);
                        remaining.set(numFound);
                    }

                });
            } catch (IOException | SolrServerException e) {
                throw new SolrRequestException("Failed to stream documents", e);
            }
        });

        return future;
    }

    @Override
    public DiscoveryNetwork network(DiscoveryNetworkDescriptor dataNetworkDescriptor) {
        final String id = dataNetworkDescriptor.getId();
        final DiscoveryNetwork dataNetwork = DiscoveryNetwork.to(id);

        try {
            final SolrParams queryParams = dataNetworkDescriptor.getSolrParams();

            final QueryResponse response = solrClient.query(COLLECTION, queryParams);

            final SolrDocumentList documents = response.getResults();

            final String dateField = dataNetworkDescriptor.getDateField();

            for (SolrDocument document : documents) {
                if (document.containsKey(dateField)) {
                    Date publicationDate = ((Date) document.getFieldValue(dateField));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(publicationDate);
                    dataNetwork.countYear(String.valueOf(calendar.get(Calendar.YEAR)));
                }
                List<String> values = getValues(document, dataNetworkDescriptor.getDataFields());

                String iid = (String) document.getFieldValue(ID);

                for (String v1 : values) {
                    dataNetwork.index(v1);

                    if (!v1.endsWith(id)) {
                        dataNetwork.countLink(v1);
                    }
                    for (String v2 : values) {
                        // prefer id as source
                        if (v2.endsWith(id)) {
                            dataNetwork.map(iid, v2, v1);
                        } else {
                            dataNetwork.map(iid, v1, v2);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to build data network!", e);
        }

        return dataNetwork;
    }

    private long count(String q) {
        SolrQuery query = new SolrQuery(q)
            .setRows(0);

        return count(query);
    }

    private long count(SolrQuery query) {
        try {
            return solrClient.query(COLLECTION, query)
                .getResults()
                .getNumFound();
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to count documents", e);
        }
    }

    private Individual getById(String id) {
        try {
            SolrDocument document = solrClient.getById(COLLECTION, id);

            return solrClient.getBinder()
                .getBean(Individual.class, document);
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to get document by id", e);
        }
    }

    private List<Individual> findAll(SolrQuery query) {
        try {
            return solrClient.query(COLLECTION, query)
                .getBeans(Individual.class);
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to query documents", e);
        }
    }

    private Page<Individual> findAll(SolrQuery query, Pageable pageable) {
        try {
            SolrDocumentList documents = solrClient.query(COLLECTION, query).getResults();
            List<Individual> individuals = solrClient.getBinder().getBeans(Individual.class, documents);

            return new PageImpl<Individual>(individuals, pageable, documents.getNumFound());
        } catch (IOException | SolrServerException e) {
            throw new SolrRequestException("Failed to query documents", e);
        }
    }

    private List<String> getValues(SolrDocument document, List<String> dataFields) {
        return dataFields.stream()
            .filter(v -> document.containsKey(v))
            .flatMap(v -> document.getFieldValues(v).stream())
            .map(v -> (String) v)
            .collect(Collectors.toList());
    }

    private class SolrQueryBuilder {

        private final SolrQuery query;

        private SolrQueryBuilder() {
            this(DEFAULT_QUERY);
        }

        private SolrQueryBuilder(String query) {
            this.query = new SolrQuery()
                .setParam("defType", defType)
                .setParam("q.op", defaultOperator)
                .setQuery(query);
        }

        public SolrQueryBuilder withQuery(QueryArg query) {

            if (StringUtils.isNotEmpty(query.getDefaultField())) {
                this.query.setParam("df", query.getDefaultField());
            }

            if (StringUtils.isNotEmpty(query.getMinimumShouldMatch())) {
                this.query.setParam("mm", query.getMinimumShouldMatch());
            }

            if (StringUtils.isNotEmpty(query.getQueryField())) {
                this.query.setParam("qf", query.getQueryField());
            }

            if (StringUtils.isNotEmpty(query.getBoostQuery())) {
                this.query.setParam("bq", query.getBoostQuery());
            }

            if (StringUtils.isNotEmpty(query.getFields())) {
                String fields = String.join(REQUEST_PARAM_DELIMETER, ID, CLASS, query.getFields());
                String fl = String.join(REQUEST_PARAM_DELIMETER, Arrays.stream(fields.split(REQUEST_PARAM_DELIMETER)).collect(Collectors.toSet()));
                this.query.setParam("fl", fl);
            }

            this.query.setQuery(query.getExpression());

            return this;
        }

        public SolrQueryBuilder withPage(Pageable page) {
            return withStart((int) page.getOffset())
                .withRows(page.getPageSize())
                .withSort(page.getSort());
        }

        public SolrQueryBuilder withStart(int start) {
            this.query.setStart(start);

            return this;
        }

        public SolrQueryBuilder withRows(int rows) {
            this.query.setRows(rows);

            return this;
        }

        public SolrQueryBuilder withSort(Sort sort) {
            sort.iterator().forEachRemaining(order -> {
                this.query.addSort(order.getProperty(), order.getDirection().isAscending() ? ORDER.asc: ORDER.desc);
            });

            return this;
        }

        public SolrQueryBuilder withFilters(List<FilterArg> filters) {
            filters.stream().collect(Collectors.groupingBy(w -> w.getField())).forEach((field, filterList) -> {
                FilterArg firstOne = filterList.get(0);
                StringBuilder filterQuery = new StringBuilder()
                    .append(new FilterQueryBuilder(firstOne, false).build());
                if (filterList.size() > 1) {
                    // NOTE: filters grouped by field are AND together
                    for (FilterArg arg : filterList.subList(1, filterList.size())) {
                        filterQuery.append(" AND ")
                            .append(new FilterQueryBuilder(arg, true).build());
                    }
                }
                this.query.addFilterQuery(filterQuery.toString());
            });

            return this;
        }

        public SolrQueryBuilder withFacets(List<FacetArg> facets) {
            facets.forEach(facet -> {
                String name = facet.getCommand();
                switch (facet.getType()) {
                    case NUMBER_RANGE:
                        Integer rangeStart = Integer.parseInt(facet.getRangeStart());
                        Integer rangeEnd = Integer.parseInt(facet.getRangeEnd());
                        Integer rangeGap = Integer.parseInt(facet.getRangeGap());
                        this.query.addNumericRangeFacet(name, rangeStart, rangeEnd, rangeGap);
                        break;
                    default:
                        this.query.addFacetField(name);
                        break;
                }
            });

            if (!facets.isEmpty()) {
                // NOTE: other possible; method, minCount, missing, and prefix
                this.query.setFacet(true);
                this.query.setFacetLimit(-1);
                this.query.setFacetMinCount(1);
            }

            return this;
        }

        public SolrQueryBuilder withBoosts(List<BoostArg> boosts) {
            final String query = this.query.getQuery();
            StringBuilder boostedQuery = new StringBuilder(query);
            boosts.forEach(boost -> {
                boostedQuery.append(" OR ")
                    .append("(")
                    .append(boost.getField())
                    .append(":")
                    .append("(")
                    .append(query)
                    .append(")")
                    .append("^")
                    .append(boost.getValue())
                    .append(")");
            });

            this.query.setQuery(boostedQuery.toString());

            return this;
        }

        public SolrQueryBuilder withHighlight(HighlightArg highlight) {

            for (String field : highlight.getFields()) {
                this.query.addHighlightField(field);
            }

            if (highlight.getFields().length > 0) {
                this.query.setHighlight(true);
                this.query.setHighlightFragsize(0);
                if (StringUtils.isNotEmpty(highlight.getPrefix())) {
                    this.query.setHighlightSimplePre(highlight.getPrefix());
                }
                if (StringUtils.isNotEmpty(highlight.getPostfix())) {
                    this.query.setHighlightSimplePost(highlight.getPostfix());
                }
            }

            return this;
        }

        public SolrQuery query() {
            return this.query;
        }

    }

    public class FilterQueryBuilder {

        private final FilterArg filter;

        private final boolean skipTag;

        public FilterQueryBuilder(FilterArg filter) {
            this.filter = filter;
            this.skipTag = false;
        }

        public FilterQueryBuilder(FilterArg filter, boolean skipTag) {
            this.filter = filter;
            this.skipTag = skipTag;
        }

        public String build() {
            String field = skipTag ? filter.getField() : filter.getCommand();
            String value = filter.getValue();

            StringBuilder filterQuery = new StringBuilder()
                .append(field)
                .append(QUERY_DELIMETER);

            switch (filter.getOpKey()) {
                case BETWEEN:
                    Matcher rangeMatcher = RANGE_PATTERN.matcher(value);
                    if (rangeMatcher.matches()) {
                        String start = rangeMatcher.group(1);
                        String end = rangeMatcher.group(2);

                        // NOTE: hard coded inclusive start exclusive end
                        filterQuery
                            .append("[")
                            .append(start)
                            .append(" TO ")
                            .append(end)
                            .append("}");

                        // NOTE: if date field, must be ISO format for Solr to recognize
                        // https://lucene.apache.org/solr/7_5_0/solr-core/org/apache/solr/schema/DatePointField.html
                        
                    } else {
                        filterQuery
                            .append("\"")
                            .append(value)
                            .append("\"");
                    }
                    break;
                case ENDS_WITH:
                    filterQuery
                        .append(value)
                        .append("}");
                    break;
                case EQUALS:
                    filterQuery
                        .append("\"")
                        .append(value)
                        .append("\"");
                    break;
                case FUZZY:
                    // NOTE: only supporting single-word terms and default edit distance of 2
                    filterQuery
                        .append(value)
                        .append("~");
                    break;
                case NOT_EQUALS:
                    filterQuery
                        .append("!")
                        .append("\"")
                        .append(value)
                        .append("\"");
                    break;
                case STARTS_WITH:
                    filterQuery
                        .append("{!")
                        .append(value);
                    break;
                case CONTAINS:
                case EXPRESSION:
                case RAW:
                    filterQuery
                        .append(value);
                default:
                    break;
            }

            return filterQuery.toString();
        }

    }

}
