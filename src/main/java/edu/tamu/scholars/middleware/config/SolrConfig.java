package edu.tamu.scholars.middleware.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class SolrConfig {

    @Value("${spring.data.solr.host:http://localhost:8983/solr}")
    private String solrHost;

    @Bean
    public SolrClient solrClient() {
        return new Http2SolrClient.Builder(solrHost)
            .connectionTimeout(900000)
            .maxConnectionsPerHost(4)
            .build();
    }

}
