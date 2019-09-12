package edu.tamu.scholars.middleware.discovery;

import static org.springframework.data.solr.core.query.Criteria.WILDCARD;

public class DiscoveryConstants {

    public static String EXPORT_INDIVIDUAL_KEY;

    public static final String ID = "id";

    public static final String CLASS = "class";

    public static final String SCORE = "score";

    public static final String MOD_TIME = "modTime";

    public static final String DEFAULT_QUERY = String.format("%s:%s", WILDCARD, WILDCARD);

    public static final String NESTED_DELIMITER = "::";

    public static final String REQUEST_PARAM_DELIMETER = ",";

    public static final String PATH_DELIMETER_REGEX = "\\.";

    public static final String EMPTY_STRING = "";

    public static final String DISCOVERY_MODEL_PACKAGE = "edu.tamu.scholars.middleware.discovery.model";

}
