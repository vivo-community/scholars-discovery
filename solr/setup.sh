#!/bin/bash

set -e

# allow easier debugging with `docker run -e VERBOSE=yes`
if [[ "$VERBOSE" = "yes" ]]; then
  set -x
fi

# allow easier reset core with `docker run -e RESET_CORE=true`
if [[ "$RESET_CORE" = "true" ]]; then
  echo 'Removing core /var/solr/data/scholars-discovery'
  rm -rf /var/solr/data/scholars-discovery
fi

if [ ! -f "/var/solr/data/scholars-discovery/core.properties" ]; then
  start-local-solr
  solr create -c scholars-discovery -d "/opt/solr/server/solr/configsets/scholars-discovery" -p 8983
  stop-local-solr
else
  echo "scholars-discovery collection already exists";
fi

exec solr -f
