FROM solr:7.7.3-slim

RUN chmod -R ugo+w /opt/solr/server/solr/
RUN chmod -R ugo+w /opt/solr/server/logs/

COPY conf /opt/solr/server/solr/configsets/scholars-discovery/conf
COPY setup.sh /setup.sh

USER root

RUN chown -R solr:solr /opt/solr/server/solr/configsets/scholars-discovery
RUN chmod -R 755 /opt/solr/server/solr/configsets/scholars-discovery

USER solr

CMD ["/bin/bash", "/setup.sh"]
