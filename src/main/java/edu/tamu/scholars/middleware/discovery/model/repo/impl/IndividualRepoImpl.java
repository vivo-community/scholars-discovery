package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.CLASS;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DEFAULT_QUERY;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.MOD_TIME;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.NESTED_DELIMITER;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.PARENTHESES_TEMPLATE;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.REQUEST_PARAM_DELIMETER;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.TYPE;
import static org.springframework.data.solr.core.query.Criteria.WILDCARD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.FacetParams.FacetRangeInclude;
import org.apache.solr.common.params.FacetParams.FacetRangeOther;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.mapping.SimpleSolrMappingContext;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.FacetOptions.FieldWithFacetParameters;
import org.springframework.data.solr.core.query.FacetOptions.FieldWithNumericRangeParameters;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.Query.Operator;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.FacetAndHighlightPage;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.HighlightArg;
import edu.tamu.scholars.middleware.discovery.argument.QueryArg;
import edu.tamu.scholars.middleware.discovery.dto.DataNetwork;
import edu.tamu.scholars.middleware.discovery.dto.DataNetworkDescriptor;
import edu.tamu.scholars.middleware.discovery.dto.DirectedData;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.custom.SolrDocumentRepoCustom;
import edu.tamu.scholars.middleware.discovery.query.CustomSimpleFacetAndHighlightQuery;
import edu.tamu.scholars.middleware.discovery.query.CustomSimpleFacetQuery;
import edu.tamu.scholars.middleware.discovery.query.parser.CustomSimpleFacetAndHighlightQueryParser;
import edu.tamu.scholars.middleware.discovery.query.parser.CustomSimpleFacetQueryParser;
import edu.tamu.scholars.middleware.model.OpKey;
import io.micrometer.core.instrument.util.StringUtils;

public class IndividualRepoImpl implements SolrDocumentRepoCustom<Individual> {
    
    private static final Logger logger = LoggerFactory.getLogger(IndividualRepoImpl.class);

    private static final Pattern RANGE_PATTERN = Pattern.compile("^\\[(.*?) TO (.*?)\\]$");

    @Value("${spring.data.solr.parser:edismax}")
    private String defType;

    @Value("${spring.data.solr.operator:AND}")
    private Operator defaultOperator;

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private SolrClient solrClient;

    @PostConstruct
    public void setup() {
        // https://jira.spring.io/browse/DATASOLR-153
        // https://github.com/spring-projects/spring-data-solr/pull/113
        solrTemplate.registerQueryParser(CustomSimpleFacetQuery.class, new CustomSimpleFacetQueryParser(new SimpleSolrMappingContext()));
        solrTemplate.registerQueryParser(CustomSimpleFacetAndHighlightQuery.class, new CustomSimpleFacetAndHighlightQueryParser(new SimpleSolrMappingContext()));
    }

    @Override
    public DataNetwork getDataNetwork(DataNetworkDescriptor dataNetworkDescriptor) {
        final String id = dataNetworkDescriptor.getId();
        final DataNetwork dataNetwork = DataNetwork.to(id);

        try {
            final SolrParams queryParams = dataNetworkDescriptor.getSolrParams();

            final QueryResponse response = solrClient.query(collection(), queryParams);

            final SolrDocumentList documents = response.getResults();

            final String dateField = dataNetworkDescriptor.getDateField();

            for (org.apache.solr.common.SolrDocument document : documents) {
                if (document.containsKey(dateField)) {
                    Date publicationDate = ((Date) document.getFieldValue(dateField));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(publicationDate);
                    dataNetwork.countYear(String.valueOf(calendar.get(Calendar.YEAR)));
                }
                List<String> values = getValues(document, dataNetworkDescriptor.getDataFields());

                for (String value : values) {
                    dataNetwork.index(value);

                    if (!value.endsWith(id)) {
                        dataNetwork.countLink(value);
                    }
                }

                for (List<String> combination : findCombinations(values)) {
                    String v0 = combination.get(0);
                    String v1 = combination.get(1);

                    String[] v0Parts = v0.split(NESTED_DELIMITER);
                    String[] v1Parts = v1.split(NESTED_DELIMITER);

                    // continue if either missing id
                    if (v0Parts.length <= 1 || v1Parts.length <= 1) {
                        continue;
                    }

                    // prefer id as source
                    if (v1Parts[1].equals(id)) {
                        dataNetwork.map(DirectedData.of(v1Parts[1], v0Parts[1]));
                    } else {
                        dataNetwork.map(DirectedData.of(v0Parts[1], v1Parts[1]));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to build data network!", e);
        }

        return dataNetwork;
    }

    private List<String> getValues(org.apache.solr.common.SolrDocument document, List<String> dataFields) {
        return dataFields.stream()
            .filter(v -> document.containsKey(v))
            .flatMap(v -> document.getFieldValues(v).stream())
            .map(v -> (String) v)
            .collect(Collectors.toList());
    }

    private Set<List<String>> findCombinations(List<String> array) {
        Set<List<String>> subarrays = new HashSet<>();
        findCombinations(array, 0, 2, subarrays, new ArrayList<>());
        return subarrays;
    }

    private void findCombinations(List<String> array, int i, int k, Set<List<String>> subarrays, List<String> out) {
        if (array.size() == 0 || k > array.size()) {
            return;
        }

        if (k == 0) {
            subarrays.add(new ArrayList<>(out));
            return;
        }

        for (int j = i; j < array.size(); j++) {
            out.add(array.get(j));
            findCombinations(array, j + 1, k - 1, subarrays, out);
            out.remove(out.size() - 1);
        }
    }

    @Override
    public long count(String query, List<FilterArg> filters) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(buildQueryCriteria(query));
        return solrTemplate.count(collection(), simpleQuery, type());
    }

    @Override
    public List<Individual> findByType(String type, List<FilterArg> filters) {
        filters.add(FilterArg.of(TYPE, Optional.of(type), Optional.of(OpKey.EQUALS.getKey()), Optional.empty()));
        return findAll(filters);
    }

    @Override
    public List<Individual> findMostRecentlyUpdate(Integer limit) {
        return findMostRecentlyUpdate(limit, new ArrayList<FilterArg>());
    }

    @Override
    public List<Individual> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(buildQueryCriteria(DEFAULT_QUERY));
        simpleQuery.addSort(Sort.by(MOD_TIME).descending());
        simpleQuery.setRows(limit);
        return solrTemplate.query(collection(), simpleQuery, type()).getContent();
    }

    @Override
    public List<Individual> findAll(List<FilterArg> filters) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(buildQueryCriteria(DEFAULT_QUERY));
        simpleQuery.setRows(Integer.MAX_VALUE);
        return solrTemplate.query(collection(), simpleQuery, type()).getContent();
    }

    @Override
    public List<Individual> findAll(List<FilterArg> filters, Sort sort) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(buildQueryCriteria(DEFAULT_QUERY));
        simpleQuery.addSort(sort);
        simpleQuery.setRows(Integer.MAX_VALUE);
        return solrTemplate.query(collection(), simpleQuery, type()).getContent();
    }

    @Override
    public Page<Individual> findAll(List<FilterArg> filters, Pageable page) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(buildQueryCriteria(DEFAULT_QUERY));
        simpleQuery.setPageRequest(page);
        return solrTemplate.queryForPage(collection(), simpleQuery, type());
    }

    @Override
    public FacetAndHighlightPage<Individual> search(QueryArg query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, HighlightArg highlight, Pageable page) {
        CustomSimpleFacetAndHighlightQuery advancedQuery = new CustomSimpleFacetAndHighlightQuery();

        Criteria criteria = buildBoostedQueryCriteria(query.getExpression(), boosts);

        advancedQuery.addCriteria(criteria);

        // NOTE: solr does not return total number of facet entries, nor afford direction of sort
        FacetOptions facetOptions = new FacetOptions();

        facets.forEach(facet -> {
            String name = facet.getCommand();
            switch (facet.getType()) {
            case NUMBER_RANGE:
                Integer rangeStart = Integer.parseInt(facet.getRangeStart());
                Integer rangeEnd = Integer.parseInt(facet.getRangeEnd());
                Integer rangeGap = Integer.parseInt(facet.getRangeGap());
                facetOptions.addFacetByRange(new FieldWithNumericRangeParameters(name, rangeStart, rangeEnd, rangeGap)
                    .setHardEnd(false)
                    .setOther(FacetRangeOther.BETWEEN)
                    .setInclude(FacetRangeInclude.LOWER));
                break;
            default:
                // NOTE: other possible; method, minCount, missing, and prefix
                facetOptions.addFacetOnField(new FieldWithFacetParameters(name));
                break;
            }
        });

        if (facetOptions.hasFacets()) {
            facetOptions.setFacetLimit(-1);
            advancedQuery.setFacetOptions(facetOptions);
        }

        buildFilterQueries(filters).forEach(filterQuery -> {
            advancedQuery.addFilterQuery(filterQuery);
        });

        advancedQuery.setDefaultOperator(defaultOperator);

        advancedQuery.setDefType(defType);

        advancedQuery.setPageRequest(page);

        if (StringUtils.isNotEmpty(query.getDefaultField())) {
            advancedQuery.setDefaultField(query.getDefaultField());
        }

        if (StringUtils.isNotEmpty(query.getMinimumShouldMatch())) {
            advancedQuery.setMinimumShouldMatch(query.getMinimumShouldMatch());
        }

        if (StringUtils.isNotEmpty(query.getQueryField())) {
            advancedQuery.setQueryField(query.getQueryField());
        }

        if (StringUtils.isNotEmpty(query.getBoostQuery())) {
            advancedQuery.setBoostQuery(query.getBoostQuery());
        }

        if (StringUtils.isNotEmpty(query.getFields())) {
            advancedQuery.addProjectionOnFields(buildFields(query));
        }

        if (ArrayUtils.isNotEmpty(highlight.getFields())) {
            HighlightOptions highlightOptions = new HighlightOptions();
            highlightOptions.setFragsize(0);
            highlightOptions.addField(highlight.getFields());
            if (StringUtils.isNotBlank(highlight.getPrefix())) {
                highlightOptions.setSimplePrefix(highlight.getPrefix());
            }
            if (StringUtils.isNotBlank(highlight.getPostfix())) {
                highlightOptions.setSimplePostfix(highlight.getPostfix());
            }
            advancedQuery.setHighlightOptions(highlightOptions);
        }

        return solrTemplate.queryForFacetAndHighlightPage(collection(), advancedQuery, type());
    }

    @Override
    public Cursor<Individual> stream(QueryArg query, List<FilterArg> filters, List<BoostArg> boosts, Sort sort) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);

        Criteria criteria = buildBoostedQueryCriteria(query.getExpression(), boosts);

        simpleQuery.addCriteria(criteria);
        simpleQuery.addSort(sort.and(Sort.by(Direction.ASC, ID)));

        if (StringUtils.isNotEmpty(query.getFields())) {
            simpleQuery.addProjectionOnFields(buildFields(query));
        }

        return solrTemplate.queryForCursor(collection(), simpleQuery, type());
    }

    public String collection() {
        return type().getAnnotation(SolrDocument.class).collection();
    }

    private Criteria buildQueryCriteria(String query) {
        return query.equals(DEFAULT_QUERY) ? new Criteria(WILDCARD).expression(WILDCARD) : new SimpleStringCriteria(query);
    }

    private Criteria buildBoostedQueryCriteria(String query, List<BoostArg> boosts) {
        if (query.equals(DEFAULT_QUERY)) {
            return new Criteria(WILDCARD).expression(WILDCARD);
        }
        Criteria criteria = new SimpleStringCriteria(query).connect();
        String expression = String.format(PARENTHESES_TEMPLATE, query);
        boosts.stream().map(boost -> Criteria.where(boost.getField()).expression(expression).boost(boost.getValue())).forEach(boostCriteria -> {
            criteria.or(boostCriteria.connect());
        });
        return criteria;
    }

    private String buildFields(QueryArg query) {
        String fields = String.join(REQUEST_PARAM_DELIMETER, ID, CLASS, query.getFields());
        return String.join(REQUEST_PARAM_DELIMETER, Arrays.stream(fields.split(REQUEST_PARAM_DELIMETER)).collect(Collectors.toSet()));
    }

    private SimpleQuery buildSimpleQuery(List<FilterArg> filters) {
        SimpleQuery simpleQuery = new SimpleQuery();
        buildFilterQueries(filters).forEach(filterQuery -> {
            simpleQuery.addFilterQuery(filterQuery);
        });
        simpleQuery.setDefaultOperator(defaultOperator);
        simpleQuery.setDefType(defType);
        return simpleQuery;
    }

    private List<SimpleFilterQuery> buildFilterQueries(List<FilterArg> filters) {
        final List<SimpleFilterQuery> results = new ArrayList<SimpleFilterQuery>();
        filters.stream().collect(Collectors.groupingBy(w -> w.getField())).forEach((field, filterList) -> {
            FilterArg firstOne = filterList.get(0);
            Criteria criteria = new CriteriaBuilder(firstOne).buildCriteria();
            // the rest (of that field) are AND'd
            // NOTE: a solution for supporting Intersection or Union could be to add another filter value delimiter to allow to specify AND/OR
            if (filterList.size() > 1) {
                for (FilterArg arg : filterList.subList(1, filterList.size())) {
                    criteria = criteria.and(new CriteriaBuilder(arg).skipTag(true).buildCriteria());
                }
            }
            SimpleFilterQuery result = new SimpleFilterQuery(criteria);
            results.add(result);
        });
        return results;
    }

    @Override
    public Class<Individual> type() {
        return Individual.class;
    }

    public class CriteriaBuilder {

        private FilterArg filter;

        private Boolean skipTag = false; // this has a default

        public CriteriaBuilder(FilterArg filter) {
            this.filter = filter;
        }

        public Criteria buildCriteria() {
            String field = skipTag ? filter.getField() : filter.getCommand();
            String value = filter.getValue();
            Criteria criteria = Criteria.where(field);
            switch (filter.getOpKey()) {
            case BETWEEN:
                Matcher rangeMatcher = RANGE_PATTERN.matcher(value);
                if (rangeMatcher.matches()) {
                    String start = rangeMatcher.group(1);
                    String end = rangeMatcher.group(2);
                    // NOTE: if date field, must be ISO format for Solr to recognize
                    // https://lucene.apache.org/solr/7_5_0/solr-core/org/apache/solr/schema/DatePointField.html
                    criteria.between(start, end, true, false);
                } else {
                    criteria.is(value);
                }
                break;
            case CONTAINS:
                criteria.contains(value);
                break;
            case ENDS_WITH:
                criteria.endsWith(value);
                break;
            case EQUALS:
                criteria.is(value);
                break;
            case EXPRESSION:
                criteria.expression(value);
                break;
            case FUZZY:
                // NOTE: more arguments can be used for fuzzy compare, yet unsupported
                criteria.fuzzy(value);
                break;
            case NOT_EQUALS:
                criteria.is(value).not();
                break;
            case STARTS_WITH:
                criteria.startsWith(value);
                break;
            case RAW:
                criteria = new SimpleStringCriteria(String.format("%s:%s", field, value));
            default:
                break;
            }
            return criteria;
        }

        public CriteriaBuilder skipTag(Boolean skipTag) {
            this.skipTag = skipTag;
            return this;
        }

    }

}
