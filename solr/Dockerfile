FROM solr:7.7.2-slim

RUN precreate-core persons
RUN precreate-core organizations
RUN precreate-core documents
RUN precreate-core relationships
RUN precreate-core processes
RUN precreate-core collections
RUN precreate-core concepts

COPY --chown=solr:solr config /opt/solr/server/solr/mycores/persons/conf
COPY --chown=solr:solr config /opt/solr/server/solr/mycores/organizations/conf
COPY --chown=solr:solr config /opt/solr/server/solr/mycores/documents/conf
COPY --chown=solr:solr config /opt/solr/server/solr/mycores/relationships/conf
COPY --chown=solr:solr config /opt/solr/server/solr/mycores/processes/conf
COPY --chown=solr:solr config /opt/solr/server/solr/mycores/collections/conf
COPY --chown=solr:solr config /opt/solr/server/solr/mycores/concepts/conf
