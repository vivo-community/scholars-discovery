package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.CLASS;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.MOD_TIME;
import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.SCORE;
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
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.custom.SolrDocumentRepoCustom;
import edu.tamu.scholars.middleware.utility.DateFormatUtility;

public abstract class AbstractSolrDocumentRepoImpl<D extends AbstractSolrDocument> implements SolrDocumentRepoCustom<D> {

    private static final String DEFAULT_QUERY = String.format("%s:%s", WILDCARD, WILDCARD);

    private static final Pattern RANGE_PATTERN = Pattern.compile("^\\[(.*?) TO (.*?)\\]$");

    private static final DateFormat YEAR_DATE_FORMAT = new SimpleDateFormat("yyyy");

    @Value("${spring.data.solr.parser:edismax}")
    private String queryParser;

    @Value("${spring.data.solr.operator:AND}")
    private Operator queryOperator;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public FacetPage<D> search(String query, List<FacetArg> facets, List<FilterArg> filters, List<BoostArg> boosts, Pageable page) {
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
            // FieldWithFacetParameters fieldWithFacetParameters = new FieldWithFacetParameters(facet.getPath(type()));
            FieldWithFacetParameters fieldWithFacetParameters = new FieldWithFacetParameters(facet.getField());

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
    public Cursor<D> stream(String query, List<FilterArg> filters, List<BoostArg> boosts, Sort sort) {
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

    @Override
    public List<D> findMostRecentlyUpdate(Integer limit) {
        return findMostRecentlyUpdate(limit, new ArrayList<FilterArg>());
    }

    @Override
    public List<D> findMostRecentlyUpdate(Integer limit, List<FilterArg> filters) {
        SimpleQuery simpleQuery = buildSimpleQuery(filters);
        simpleQuery.addCriteria(getQueryCriteria(DEFAULT_QUERY));
        simpleQuery.addSort(Sort.by(MOD_TIME).descending());
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

    protected List<SimpleFilterQuery> buildFilterQueries(List<FilterArg> filters) {
        List<SimpleFilterQuery> filterQueries = filters.stream().map(filter -> new SimpleFilterQuery(buildCriteria(filter))).collect(Collectors.toList());
        filterQueries.add(new SimpleFilterQuery(Criteria.where(CLASS).is(type().getSimpleName())));
        return filterQueries;
    }

    protected Criteria buildCriteria(FilterArg filter) {
        // String field = filter.getPath(type());
        String field = filter.getField();
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
        default:
            break;
        }
        return criteria;
    }

}
