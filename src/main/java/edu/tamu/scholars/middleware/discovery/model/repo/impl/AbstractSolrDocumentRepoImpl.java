package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static org.springframework.data.solr.core.query.Criteria.WILDCARD;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import edu.tamu.scholars.middleware.discovery.argument.IndexArg;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.custom.SolrDocumentRepoCustom;
import edu.tamu.scholars.middleware.utility.DateFormatUtility;

public abstract class AbstractSolrDocumentRepoImpl<D extends AbstractSolrDocument> implements SolrDocumentRepoCustom<D> {

    private static final String DEFAULT_QUERY = String.format("%s:%s", WILDCARD, WILDCARD);

    private static final String SCORE_FIELD = "score";

    private static final String MOD_TIME_FIELD = "modTime";

    private static final Pattern RANGE_PATTERN = Pattern.compile("^\\[(.*?) TO (.*?)\\]$");

    private static final DateFormat YEAR_DATE_FORMAT = new SimpleDateFormat("yyyy");

    @Value("${spring.data.solr.parser:edismax}")
    private String queryParser;

    @Value("${spring.data.solr.operator:AND}")
    private Operator queryOperator;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public FacetPage<D> search(String query, Optional<IndexArg> index, List<FacetArg> facetArguments, List<FilterArg> filters, List<BoostArg> boosts, Pageable page) {
        FacetQuery facetQuery = new SimpleFacetQuery();

        Criteria criteria = getQueryCriteria(query);

        Optional<Criteria> boostCriteria = getBoostCriteria(query, boosts);

        if (boostCriteria.isPresent()) {
            criteria = boostCriteria.get().or(criteria);
            page = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(SCORE_FIELD).descending().and(page.getSort()));
        }

        facetQuery.addCriteria(criteria);

        if (index.isPresent()) {
            facetQuery.addFilterQuery(new SimpleFilterQuery(buildCriteria(index.get())));
        }

        FacetOptions facetOptions = new FacetOptions();

        facetArguments.forEach(facetArg -> {
            FieldWithFacetParameters fieldWithFacetParameters = new FieldWithFacetParameters(facetArg.getPath(type()));

            fieldWithFacetParameters.setLimit(Integer.MAX_VALUE);

            fieldWithFacetParameters.setOffset(0);

            // NOTE: solr does not return total number of facet entries, nor afford direction of sort

            // NOTE: other possible; method, minCount, missing, and prefix

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
    public Cursor<D> stream(String query, Optional<IndexArg> index, List<FilterArg> filters, List<BoostArg> boosts, Sort sort) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);

        Criteria criteria = getQueryCriteria(query);

        Optional<Criteria> boostCriteria = getBoostCriteria(query, boosts);

        if (boostCriteria.isPresent()) {
            criteria = boostCriteria.get().or(criteria);
            sort = Sort.by(SCORE_FIELD).descending().and(sort);
        }

        if (index.isPresent()) {
            simpleQuery.addFilterQuery(new SimpleFilterQuery(buildCriteria(index.get())));
        }

        simpleQuery.addCriteria(criteria);
        simpleQuery.addSort(sort.and(Sort.by(Direction.ASC, ID)));
        return solrTemplate.queryForCursor(collection(), simpleQuery, type());
    }

    @Override
    public List<D> findMostRecentlyUpdate(Integer limit) {
        return findMostRecentlyUpdate(limit, new ArrayList<FilterArg>());
    }

    @Override
    public List<D> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(getQueryCriteria(DEFAULT_QUERY));
        simpleQuery.addSort(Sort.by(MOD_TIME_FIELD).descending());
        simpleQuery.setRows(limit);
        return solrTemplate.query(collection(), simpleQuery, type()).getContent();
    }

    @Override
    public long count(String query, List<FilterArg> filters) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(getQueryCriteria(query));
        return solrTemplate.count(collection(), simpleQuery, type());
    }

    public String collection() {
        return type().getAnnotation(SolrDocument.class).collection();
    }

    private Criteria getQueryCriteria(String query) {
        return query.equals(DEFAULT_QUERY) ? new Criteria(WILDCARD).expression(WILDCARD) : new SimpleStringCriteria(query);
    }

    private Optional<Criteria> getBoostCriteria(String query, List<BoostArg> boosts) {
        return query.equals(DEFAULT_QUERY) ? Optional.empty() : boosts.stream().map(boost -> Criteria.where(boost.getField()).is(query).boost(boost.getValue())).reduce((c1, c2) -> c1.or(c2));
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
        return filters.stream().map(filter -> {
            Criteria criteria;
            String value = filter.getValue();
            Matcher rangeMatcher = RANGE_PATTERN.matcher(value);
            if (rangeMatcher.matches()) {
                String start = rangeMatcher.group(1);
                String end = rangeMatcher.group(2);
                try {
                    Date from = YEAR_DATE_FORMAT.parse(start);
                    Date to = YEAR_DATE_FORMAT.parse(end);
                    criteria = new Criteria(filter.getPath(type())).between(from, to, true, false);
                } catch (ParseException e) {
                    try {
                        LocalDate from = DateFormatUtility.parse(start);
                        LocalDate to = DateFormatUtility.parse(end);
                        criteria = new Criteria(filter.getPath(type())).between(from, to, true, false);
                    } catch (DateTimeParseException dtpe) {
                        criteria = new SimpleStringCriteria(String.format("%s:%s", filter.getPath(type()), value));
                    }
                }
            } else {
                criteria = new Criteria(filter.getPath(type())).is(value);
            }
            return new SimpleFilterQuery(criteria);
        }).collect(Collectors.toList());
    }

    private Criteria buildCriteria(IndexArg index) {
        Criteria criteria = new Criteria(index.getPath(type()));
        switch (index.getOperationKey()) {
        case BETWEEN:
            // NOTE: not supported yet
            break;
        case CONTAINS:
            criteria.contains(index.getOption());
            break;
        case ENDS_WITH:
            criteria.endsWith(index.getOption());
            break;
        case EQUALS:
            criteria.is(index.getOption());
            break;
        case EXPRESSION:
            criteria.expression(index.getOption());
            break;
        case FUNCTION:
            // NOTE: not supported
            break;
        case FUZZY:
            // NOTE: more arguments can be used for fuzzy compare
            criteria.fuzzy(index.getOption());
            break;
        case NEAR:
            // NOTE: not supported yet
            break;
        case SLOPPY:
            // NOTE: not supported
            break;
        case STARTS_WITH:
            criteria.startsWith(index.getOption());
            break;
        case WITHIN:
            // NOTE: not supported yet
            break;
        default:
            break;
        }
        return criteria;
    }

}
