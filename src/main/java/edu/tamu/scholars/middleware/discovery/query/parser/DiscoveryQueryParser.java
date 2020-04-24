package edu.tamu.scholars.middleware.discovery.query.parser;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.solr.core.DefaultQueryParser;
import org.springframework.data.solr.core.QueryParserBase;
import org.springframework.data.solr.core.mapping.SolrPersistentEntity;
import org.springframework.data.solr.core.mapping.SolrPersistentProperty;
import org.springframework.data.solr.core.query.AbstractQueryDecorator;

import edu.tamu.scholars.middleware.discovery.query.DiscoveryQuery;

public class DiscoveryQueryParser<Q extends DiscoveryQuery> extends QueryParserBase<AbstractQueryDecorator> {

    private final DefaultQueryParser defaultQueryParser;

    public DiscoveryQueryParser(MappingContext<? extends SolrPersistentEntity<?>, SolrPersistentProperty> mappingContext) {
        super(mappingContext);
        defaultQueryParser = new DefaultQueryParser(mappingContext);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SolrQuery doConstructSolrQuery(AbstractQueryDecorator queryDecorator, Class<?> domainType) {

        SolrQuery solrQuery = defaultQueryParser.doConstructSolrQuery(queryDecorator, domainType);

        Q query = (Q) queryDecorator.getDecoratedQuery();

        if (StringUtils.isNotEmpty(query.getDefaultField())) {
            solrQuery.add("df", query.getDefaultField());
        }
        if (StringUtils.isNotEmpty(query.getMinimumShouldMatch())) {
            solrQuery.add("mm", query.getMinimumShouldMatch());
        }
        if (StringUtils.isNotEmpty(query.getQueryField())) {
            solrQuery.add("qf", query.getQueryField());
        }
        if (StringUtils.isNotEmpty(query.getBoostQuery())) {
            solrQuery.add("bq", query.getBoostQuery());
        }

        return solrQuery;
    }

}