package edu.tamu.scholars.middleware.discovery.query.parser;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.solr.core.mapping.SolrPersistentEntity;
import org.springframework.data.solr.core.mapping.SolrPersistentProperty;

import edu.tamu.scholars.middleware.discovery.query.CustomSimpleFacetAndHighlightQuery;

public class CustomSimpleFacetAndHighlightQueryParser extends CustomSimpleQueryParser<CustomSimpleFacetAndHighlightQuery> {

    public CustomSimpleFacetAndHighlightQueryParser(MappingContext<? extends SolrPersistentEntity<?>, SolrPersistentProperty> mappingContext) {
        super(mappingContext);
    }

}