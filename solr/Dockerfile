FROM solr:7.7.1

WORKDIR /opt/solr/server/solr/
ADD . /opt/solr/server/solr/

USER root
RUN chown -R solr /opt/solr/server/solr
RUN chmod ugo+w -R /opt/solr/server/logs
USER solr

RUN mkdir -p /opt/solr/server/solr/persons
RUN precreate-core persons

RUN mkdir -p /opt/solr/server/solr/organizations
RUN precreate-core organizations

RUN mkdir -p /opt/solr/server/solr/documents
RUN precreate-core documents

RUN mkdir -p /opt/solr/server/solr/relationships
RUN precreate-core relationships

RUN mkdir -p /opt/solr/server/solr/processes
RUN precreate-core processes

RUN mkdir -p /opt/solr/server/solr/collections
RUN precreate-core collections

RUN mkdir -p /opt/solr/server/solr/concepts
RUN precreate-core concepts

#RUN precreate-core persons /opt/solr/server/solr/persons
#RUN precreate-core organizations /opt/solr/server/solr/organizations
#RUN precreate-core documents /opt/solr/server/solr/documents
#RUN precreate-core relationships /opt/solr/server/solr/relationships
#RUN precreate-core processes /opt/solr/server/solr/processes
#RUN precreate-core collections /opt/solr/server/solr/collections
#RUN precreate-core concepts /opt/solr/server/solr/concepts
