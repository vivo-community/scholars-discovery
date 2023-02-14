FROM solr:9.1

USER root

COPY configsets/scholars-discovery/conf /opt/solr/server/solr/configsets/scholars-discovery/conf
COPY setup.sh /opt/solr/docker/scripts/setup.sh

RUN chown -R solr:solr /opt/solr/server/solr/configsets/scholars-discovery
RUN chmod -R 755 /opt/solr/server/solr/configsets/scholars-discovery
RUN chmod -R ugo+rx /opt/solr/docker/scripts/setup.sh

USER solr

CMD ["/bin/bash", "/opt/solr/docker/scripts/setup.sh"]
