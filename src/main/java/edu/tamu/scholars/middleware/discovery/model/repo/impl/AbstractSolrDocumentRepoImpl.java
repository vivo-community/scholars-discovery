package edu.tamu.scholars.middleware.discovery.model.repo.impl;

import static edu.tamu.scholars.middleware.discovery.DiscoveryConstants.ID;
import static org.springframework.data.solr.core.query.Criteria.WILDCARD;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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

import edu.tamu.scholars.middleware.discovery.argument.FacetArg;
import edu.tamu.scholars.middleware.discovery.argument.FilterArg;
import edu.tamu.scholars.middleware.discovery.argument.IndexArg;
import edu.tamu.scholars.middleware.discovery.model.AbstractSolrDocument;
import edu.tamu.scholars.middleware.discovery.model.repo.custom.SolrDocumentRepoCustom;

public abstract class AbstractSolrDocumentRepoImpl<D extends AbstractSolrDocument> implements SolrDocumentRepoCustom<D> {

    private static final String DEFAULT_QUERY = String.format("%s:%s", WILDCARD, WILDCARD);

    private static final String SCORE_FIELD = "score";

    private static final String MOD_TIME_FIELD = "modTime";

    @Value("${spring.data.solr.parser:edismax}")
    private String queryParser;

    @Value("${spring.data.solr.operator:AND}")
    private Operator queryOperator;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public FacetPage<D> search(String query, Optional<IndexArg> index, List<FacetArg> facets, List<FilterArg> filters, Pageable page) {
        FacetQuery facetQuery = new SimpleFacetQuery();

        if (query.equals(DEFAULT_QUERY)) {
            facetQuery.addCriteria(new Criteria(WILDCARD).expression(WILDCARD));
        } else {
            Criteria criteria = getCriteria(query);
            facetQuery.addCriteria(criteria);
            Sort scoreSort = Sort.by(SCORE_FIELD).descending().and(page.getSort());
            page = PageRequest.of(page.getPageNumber(), page.getPageSize(), scoreSort);
        }

        if (index.isPresent()) {
            facetQuery.addFilterQuery(new SimpleFilterQuery(buildCriteria(index.get())));
        }

        FacetOptions facetOptions = new FacetOptions();
        facets.forEach(facet -> {
            FieldWithFacetParameters fieldWithFacetParameters = new FieldWithFacetParameters(facet.getPath(type()));

            // NOTE: until spring fixes their Solr facet entry pagination, using max limit and zero offset
            fieldWithFacetParameters.setLimit(Integer.MAX_VALUE);

            fieldWithFacetParameters.setOffset(0);

            fieldWithFacetParameters.setSort(facet.getSort());

            // NOTE: other possible; method, minCount, missing, and prefix

            facetOptions.addFacetOnField(fieldWithFacetParameters);
        });

        if (facetOptions.hasFields()) {
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
    public Cursor<D> stream(String query, Optional<IndexArg> index, List<FilterArg> filters, Sort sort) {
        SimpleQuery simpleQuery = buildSimpleQuery(query, filters);
        if (index.isPresent()) {
            simpleQuery.addFilterQuery(new SimpleFilterQuery(buildCriteria(index.get())));
        }
        Sort scoreSort = Sort.by(SCORE_FIELD).descending().and(sort);
        simpleQuery.addSort(scoreSort.and(Sort.by(Direction.ASC, ID)));
        return solrTemplate.queryForCursor(collection(), simpleQuery, type());
    }

    @Override
    public List<D> findMostRecentlyUpdate(Integer limit) {
        SimpleQuery simpleQuery = buildSimpleQuery();
        simpleQuery.addSort(Sort.by(MOD_TIME_FIELD).descending());
        simpleQuery.setRows(limit);
        return solrTemplate.query(collection(), simpleQuery, type()).getContent();
    }

    @Override
    public long count(String query, List<FilterArg> filters) {
        SimpleQuery simpleQuery = buildSimpleQuery(query, filters);
        return solrTemplate.count(collection(), simpleQuery, type());
    }

    public String collection() {
        return type().getAnnotation(SolrDocument.class).collection();
    }

    protected Criteria getCriteria(String query) {
        return new SimpleStringCriteria(query);
    }

    private SimpleQuery buildSimpleQuery() {
        return buildSimpleQuery(DEFAULT_QUERY, new ArrayList<FilterArg>());
    }

    private SimpleQuery buildSimpleQuery(String query, List<FilterArg> filters) {
        SimpleQuery simpleQuery = new SimpleQuery();

        if (query.equals(DEFAULT_QUERY)) {
            simpleQuery.addCriteria(new Criteria(WILDCARD).expression(WILDCARD));
        } else {
            simpleQuery.addCriteria(getCriteria(query));
        }

        buildFilterQueries(filters).forEach(filterQuery -> {
            simpleQuery.addFilterQuery(filterQuery);
        });

        simpleQuery.setDefaultOperator(queryOperator);

        simpleQuery.setDefType(queryParser);

        return simpleQuery;
    }

    public List<SimpleFilterQuery> buildFilterQueries(List<FilterArg> filters) {
        return filters.stream().map(filter -> {
            Criteria criteria;
            String value = filter.getValue();
            if (value.startsWith("[") && value.contains(" TO ") && value.endsWith("]")) {
                // TODO: how to support other date formats
                DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                String[] parts = value.substring(1, value.length() - 1).split(" TO ");
                try {
                    Date from = format.parse(parts[0]);
                    Date to = format.parse(parts[1]);
                    criteria = new Criteria(filter.getPath(type())).between(from, to, true, false);
                } catch (ParseException e) {
                    criteria = new SimpleStringCriteria(String.format("%s:%s", filter.getPath(type()), value));
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
