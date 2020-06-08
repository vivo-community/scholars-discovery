#!/bin/bash

set -e

# allow easier debugging with `docker run -e VERBOSE=yes`
if [[ "$VERBOSE" = "yes" ]]; then
    set -x
fi

if [ ! -f "/opt/solr/server/solr/mycores/scholars-discovery/core.properties" ]; then
  start-local-solr
  solr create -c scholars-discovery -d "/opt/solr/server/solr/configsets/scholars-discovery" -p 8983
  stop-local-solr
  mv "/opt/solr/server/solr/scholars-discovery" /opt/solr/server/solr/mycores/
else
  echo "scholars-discovery collection already exists";
fi

exec solr -f