package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.DEFAULT_QUERY;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.MOD_TIME;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.SCORE;
import static org.springframework.data.solr.core.query.Criteria.WILDCARD;

//import org.springframework.data.solr.core.query.FacetOptions.FacetParameter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.solr.query.FilterQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.FacetOptions.FieldWithFacetParameters;
import org.springframework.data.solr.core.query.FacetQuery;
import org.springframework.data.solr.core.query.Query.Operator;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.core.query.result.Cursor;
import org.springframework.data.solr.core.query.result.FacetPage;

import edu.tamu.scholars.middleware.discovery.argument.BoostArg;
import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.model.Individual;
import edu.tamu.scholars.middleware.discovery.model.repo.custom.SolrDocumentRepoCustom;
import edu.tamu.scholars.middleware.model.OpKey;
import edu.tamu.scholars.middleware.utility.DateFormatUtility;

public class IndividualRepoImpl implements SolrDocumentRepoCustom<Individual> {

    private static final Pattern RANGE_PATTERN = Pattern.compile("^\\[(.*?) TO (.*?)\\]$");

    private static final DateFormat YEAR_DATE_FORMAT = new SimpleDateFormat("yyyy");

    @Value("${spring.data.solr.parser:edismax}")
    private String queryParser;

    @Value("${spring.data.solr.operator:AND}")
    private Operator queryOperator;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public long count(String query, List<FilterArg> filters) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(getQueryCriteria(query));
        return solrTemplate.count(collection(), simpleQuery, type());
    }

   
    @Override
    public List<Individual> findByType(String type, List<FilterArg> filters) {
        filters.add(FilterArg.of(
            "type", Optional.of(type), 
             Optional.of(OpKey.EQUALS.getKey()),
             Optional.empty()));
        return findAll(filters);
    }

    @Override
    public List<Individual> findMostRecentlyUpdate(Integer limit) {
        return findMostRecentlyUpdate(limit, new ArrayList<FilterArg>());
    }

    @Override
    public List<Individual> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(getQueryCriteria(DEFAULT_QUERY));
        simpleQuery.addSort(Sort.by(MOD_TIME).descending());
        simpleQuery.setRows(limit);
        return solrTemplate.query(collection(), simpleQuery, type()).getContent();
    }

    @Override
    public List<Individual> findAll(List<FilterArg> filters) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(getQueryCriteria(DEFAULT_QUERY));
        simpleQuery.setRows(Integer.MAX_VALUE);
        return solrTemplate.query(collection(), simpleQuery, type()).getContent();
    }

    @Override
    public List<Individual> findAll(List<FilterArg> filters, Sort sort) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(getQueryCriteria(DEFAULT_QUERY));
        simpleQuery.addSort(sort);
        simpleQuery.setRows(Integer.MAX_VALUE);
        return solrTemplate.query(collection(), simpleQuery, type()).getContent();
    }

    @Override
    public Page<Individual> findAll(List<FilterArg> filters, Pageable page) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(getQueryCriteria(DEFAULT_QUERY));
        simpleQuery.setPageRequest(page);
        return solrTemplate.queryForPage(collection(), simpleQuery, type());
    }

    @Override
    public FacetPage<Individual> search(String query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, Pageable page) {
        FacetQuery facetQuery = new SimpleFacetQuery();

        Criteria criteria = getQueryCriteria(query);

        Optional<Criteria> boostCriteria = getBoostCriteria(query, boosts);

        if (boostCriteria.isPresent()) {
            criteria = boostCriteria.get().or(criteria);
            page = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(SCORE).descending().and(page.getSort()));
        }

        facetQuery.addCriteria(criteria);

        FacetOptions facetOptions = new FacetOptions();

        facets.forEach(facet -> {
            FieldWithFacetParameters fieldWithFacetParameters = new FieldWithFacetParameters(facet.getCommand());
            facetOptions.addFacetOnField(fieldWithFacetParameters);
        });

        if (facetOptions.hasFacets()) {
            facetQuery.setFacetOptions(facetOptions);
        }

        buildFilterQueries(filters).forEach(filterQuery -> {
            facetQuery.addFilterQuery(filterQuery);
        });

        facetQuery.setDefaultOperator(queryOperator);

        facetQuery.setDefType(queryParser);

        facetQuery.setPageRequest(page);

        return solrTemplate.queryForFacetPage(collection(), facetQuery, type());
    }

    @Override
    public Cursor<Individual> stream(String query, List<FilterArg> filters, List<BoostArg> boosts, Sort sort) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);

        Criteria criteria = getQueryCriteria(query);

        Optional<Criteria> boostCriteria = getBoostCriteria(query, boosts);

        if (boostCriteria.isPresent()) {
            criteria = boostCriteria.get().or(criteria);
            sort = Sort.by(SCORE).descending().and(sort);
        }

        simpleQuery.addCriteria(criteria);
        simpleQuery.addSort(sort.and(Sort.by(Direction.ASC, ID)));
        return solrTemplate.queryForCursor(collection(), simpleQuery, type());
    }

    public String collection() {
        return type().getAnnotation(SolrDocument.class).collection();
    }

    private Criteria getQueryCriteria(String query) {
        return query.equals(DEFAULT_QUERY) ? new Criteria(WILDCARD).expression(WILDCARD) : new SimpleStringCriteria(query);
    }

    private Optional<Criteria> getBoostCriteria(String query, List<BoostArg> boosts) {
        return query.equals(DEFAULT_QUERY) ? Optional.empty() : boosts.stream().map(boost -> Criteria.where(boost.getProperty()).is(query).boost(boost.getValue())).reduce((c1, c2) -> c1.or(c2));
    }

    private SimpleQuery buildSimpleQuery(List<FilterArg> filters) {
        SimpleQuery simpleQuery = new SimpleQuery();
        buildFilterQueries(filters).forEach(filterQuery -> {
            simpleQuery.addFilterQuery(filterQuery);
        });

        simpleQuery.setDefaultOperator(queryOperator);
        simpleQuery.setDefType(queryParser);

        return simpleQuery;
    }

    private List<SimpleFilterQuery> buildFilterQueries(List<FilterArg> filters) {
        // TODO: split out the 'OR' ones so they can be chained
        Criteria conditions = null;
        String currentField = null;
        List<SimpleFilterQuery> results = new ArrayList<SimpleFilterQuery>();

        for (FilterArg filter: filters) {
             if (conditions == null) {
                conditions = buildCriteria(filter);
                currentField = filter.getField();
             } else {
                String field = filter.getField();
                // if field seen before add as OR, otherwise another (AND)
                if (field.equals(currentField)) {
                    conditions = conditions.or(buildCriteria(filter));
                } else {
                    // NOTE: and doesn't seem to work out, not sure why
                    //conditions = conditions.and(buildCriteria(filter));
                    SimpleFilterQuery result = new SimpleFilterQuery(conditions);
                    results.add(result);
                    conditions = buildCriteria(filter);
                }
                // now make current field field
                currentField = field; 
             }
        }
        // only going to be one
        SimpleFilterQuery result = new SimpleFilterQuery(conditions);
        results.add(result);
        // was this:
        //return filters.stream().map(filter -> new SimpleFilterQuery(buildCriteria(filter))).collect(Collectors.toList());
        return results;
    }

    private Criteria buildCriteria(FilterArg filter) {
        String field = filter.getCommand();
        String value = filter.getValue();
        Criteria criteria = Criteria.where(field);
        switch (filter.getOpKey()) {
        case BETWEEN:
            Matcher rangeMatcher = RANGE_PATTERN.matcher(value);
            if (rangeMatcher.matches()) {
                String start = rangeMatcher.group(1);
                String end = rangeMatcher.group(2);
                try {
                    Date from = YEAR_DATE_FORMAT.parse(start);
                    Date to = YEAR_DATE_FORMAT.parse(end);
                    criteria.between(from, to, true, false);
                } catch (ParseException e) {
                    try {
                        LocalDate from = DateFormatUtility.parse(start);
                        LocalDate to = DateFormatUtility.parse(end);
                        criteria.between(from, to, true, false);
                    } catch (DateTimeParseException dtpe) {
                        criteria = new SimpleStringCriteria(String.format("%s:%s", field, value));
                    }
                }
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

    @Override
    public Class<Individual> type() {
        return Individual.class;
    }

}
