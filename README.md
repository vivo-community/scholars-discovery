[![Build Status](https://travis-ci.org/vivo-community/scholars-discovery.svg?branch=master)](https://travis-ci.org/vivo-community/scholars-discovery)
[![Coverage Status](https://coveralls.io/repos/github/vivo-community/scholars-discovery/badge.svg?branch=master)](https://coveralls.io/github/vivo-community/scholars-discovery?branch=master)

# VIVO Scholars Discovery Middleware

VIVO Scholars Discovery is a middleware project that pulls [VIVO](https://duraspace.org/vivo/) content into its own search index (Solr) and then exposes that content via a RESTful service endpoint.

Various frontend applications are available (or can be built) to display the content as read-only websites.
Existing frontend applications include:
1. [VIVO Scholars Angular](https://github.com/vivo-community/scholars-angular)

## Installation instructions

1. [Install](https://maven.apache.org/install.html) Maven
1. [Install](https://docs.docker.com/install/) Docker
1. Clone this project
1. Build and Run the provided Solr application
```bash
   cd scholars-discovery/solr
   docker build --tag=scholars-solr .
   docker run -p 8983:8983 scholars-solr
```
1. Build and Run the application
```bash
   mvn clean install
   mvn spring-boot:run -Dspring-boot.run.config.location=src/main/resources/
```
