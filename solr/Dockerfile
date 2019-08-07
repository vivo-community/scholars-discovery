FROM solr:7.7.2

COPY --chown=solr:solr config /opt/solr/server/solr/persons
COPY --chown=solr:solr config /opt/solr/server/solr/organizations
COPY --chown=solr:solr config /opt/solr/server/solr/documents
COPY --chown=solr:solr config /opt/solr/server/solr/relationships
COPY --chown=solr:solr config /opt/solr/server/solr/processes
COPY --chown=solr:solr config /opt/solr/server/solr/collections
COPY --chown=solr:solr config /opt/solr/server/solr/concepts

USER solr

RUN precreate-core persons /opt/solr/server/solr/persons
RUN precreate-core organizations /opt/solr/server/solr/organizations
RUN precreate-core documents /opt/solr/server/solr/documents
RUN precreate-core relationships /opt/solr/server/solr/relationships
RUN precreate-core processes /opt/solr/server/solr/processes
RUN precreate-core collections /opt/solr/server/solr/collections
RUN precreate-core concepts /opt/solr/server/solr/concepts