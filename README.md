[![Java CI with Maven](https://github.com/TAMULib/scholars-discovery/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/TAMULib/scholars-discovery/actions?query=workflow%3A%22Java+CI+with+Maven%22)
[![Coverage Status](https://coveralls.io/repos/github/TAMULib/scholars-discovery/badge.svg)](https://coveralls.io/github/TAMULib/scholars-discovery)

# scholars-discovery

VIVO Scholars Discovery is a middleware project that pulls [VIVO](https://duraspace.org/vivo/) content into its own search index (Solr) and then exposes that content via a RESTful service endpoint.

Various frontend applications are available (or can be built) to display the content as read-only websites.
Existing frontend applications include:
1. [VIVO Scholars Angular](https://github.com/vivo-community/scholars-angular)
2. [VIVO Scholars React](https://github.com/vivo-community/scholars-react)

# API

[Scholars Middleware REST Service API Documentation](https://tamulib.github.io/scholars-discovery/)

# Background

Scholars Discovery project was initiated by [Scholars@TAMU](https://scholars.library.tamu.edu/) project team at Texas A&M University (TAMU) Libraries. In support of the Libraries’ goal of enabling and contextualizing the discovery of scholars and their expertise across disciplines, the Scholars’ team at TAMU Office of Scholarly Communications (OSC) proposed the Scholars version 2 project, which focuses on deploying (1) new public facing layer (Read-only), (2) faceted search engine, (3) Data reuse options, and (4) search engine optimization. Digital Initiative (DI) at TAMU Libraries collaborated with the OSC to design and implement the current system architecture including Scholars Discovery and VIVO Scholars Angular. In a later stage, Scholars Discovery project was adopted by VIVO Community’s [VIVO Scholar Task Force](https://wiki.duraspace.org/display/VIVO/VIVO+Scholar+Task+Force).

# System Architecture

![System Architecture](https://raw.githubusercontent.com/vivo-community/scholars-discovery/master/src/main/resources/scholars-discovery.png)

# Technology

Scholars discovery system is first and foremost an ETL system in which **e**xtracts data from VIVO's triplestore, **t**ransforms triples into flattened documents, and **l**oads the documents into Solr. The Solr index is then exposed via REST API and GraphQL API as a nested JSON. A secondary feature is that of providing a persistent, configurable discovery layout for rendering a UI. 

Extraction from VIVO is done view configurable harvesters in which make SPARQL requests to the triplestore for a collection of objects and subsequent SPARQL requests for each property value of the target document. The SPARQL requests can be found in [src/main/resources/templates/sparql](https://github.com/vivo-community/scholars-discovery/tree/master/src/main/resources/templates/sparql). The transformation is done granularly converting resulting triples of a SPARQL request into a property of a flattened document. This document is then saved into a heterogeneous Solr collection. The configuration of the Solr collection can be found in [solr/config](https://github.com/vivo-community/scholars-discovery/tree/master/solr/config). In order to represent a flatten document as a nested JSON response, the field values are indexed with a relationship identifier convention. ```[value]::[id]```, ```[value]::[id]::[id]```, etc. During serialization the document model is traversed parsing the Solr field value and constructing a nested JSON.

Here is a list of some dependencies used:

1. [Spring Boot](https://spring.io/projects/spring-boot)
   - [Spring Data REST](https://spring.io/projects/spring-data-rest)
   - [Spring Data for Apache Solr](https://spring.io/projects/spring-data-solr)
   - [Spring HATEOAS](https://spring.io/projects/spring-hateoas)
   - [Spring REST Docs](https://spring.io/projects/spring-restdocs)
2. [Apache Jena](https://jena.apache.org/)
3. [Apache Solr](https://lucene.apache.org/solr/)
4. [GraphQL SPQR](https://github.com/leangen/graphql-spqr)
   - [graphql-spqr-spring-boot-starter](https://github.com/leangen/graphql-spqr-spring-boot-starter)
5. [JavaPoet](https://github.com/square/javapoet)

## Configuration

The basic Spring Boot application configuration can be found at [src/main/resources/application.yml](https://github.com/vivo-community/scholars-discovery/blob/master/src/main/resources/application.yml). Here you be able to configure basic server and spring configuration as well as custom configuration for Scholars Discovery. There are several configuration POJOs to represent configurations. They can be found in [src/main/java/edu/tamu/scholars/middleware/config/model](https://github.com/vivo-community/scholars-discovery/tree/master/src/main/java/edu/tamu/scholars/middleware/config/model), [src/main/java/edu/tamu/scholars/middleware/auth/config](https://github.com/vivo-community/scholars-discovery/tree/master/src/main/java/edu/tamu/scholars/middleware/auth/config), and [src/main/java/edu/tamu/scholars/middleware/graphql/config/model](https://github.com/vivo-community/scholars-discovery/tree/master/src/main/java/edu/tamu/scholars/middleware/graphql/config/model).

### Harvesting

Harvesting can be configured via ```middleware.harvesters``` and represented with [HarvesterConfig](https://github.com/vivo-community/scholars-discovery/blob/master/src/main/java/edu/tamu/scholars/middleware/config/model/HarvesterConfig.java). For each harvester, a bean will be created in which specifies the type of harvester and which document types it maps to. The reference implementation is the local triplestore harvester.

### Indexing

Indexing can be configured via ```middleware.indexers``` and represented with [IndexerConfig](https://github.com/vivo-community/scholars-discovery/blob/master/src/main/java/edu/tamu/scholars/middleware/config/model/IndexerConfig.java). For each indexer, a bean will be created in which specifies the type of indexer and which document types it indexes. The reference implementation is the solr indexer.

The application can be configured to harvest and index on startup, ```middleware.index.onStartup```, and via a cron schedule via ```middleware.index.cron```. The indexing is done in batch for performance. It can be tuned via ```middleware.index.batchSize```.

### Solr

Solr is configured via ```spring.data.solr```.

### GraphQL

GraphQL SPQR configuration can be done via ```graphql.spqr```. Explicit Java [models](https://github.com/vivo-community/scholars-discovery/tree/master/src/main/java/edu/tamu/scholars/middleware/graphql/model) in which are turned into a GraphQL schema are generated. The generated models are a nested representation of the flattened index documents. An important configuration for GraphQL schema generation is for generating explicit composite models representing relationships between complete index documents. The configuration is done via [src/main/resources/graphql/composites.yml](https://github.com/vivo-community/scholars-discovery/blob/master/src/main/resources/graphql/composites.yml) and represented with [Composite.java](https://github.com/vivo-community/scholars-discovery/tree/master/src/main/java/edu/tamu/scholars/middleware/graphql/config/model).

## Installation instructions

1. [Install](https://maven.apache.org/install.html) Maven
2. [Install](https://docs.docker.com/install/) Docker
3. Clone this project
4. Build and Run the provided Solr application
```bash
   cd scholars-discovery/solr
   docker build --tag=scholars/solr .
   docker run -d -p 8983:8983 scholars/solr
```
5. Build and Run the application
```bash
   mvn clean install
   mvn spring-boot:run
```
   - Note: Custom application configuration can be achieved by providing a location and an optional profile, such as:
```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.config.location=/some/directory/
```
   - ..where an `application-dev.yml` exists in the `/some/location/` directory

## Docker Deployment

```bash
docker build -t scholars/discovery .
```

```bash
docker run -d -p 9000:9000 -e SPRING_APPLICATION_JSON="{\"spring\":{\"data\":{\"solr\":{\"host\":\"http://localhost:8983/solr\"}}},\"ui\":{\"url\":\"http://localhost:3000\"},\"vivo\":{\"base-url\":\"http://localhost:8080/vivo\"},\"graphql\":{\"spqr\":{\"gui\":{\"enabled\":true}}},\"middleware\":{\"allowed-origins\":[\"http://localhost:3000\"],\"index\":{\"onStartup\":false},\"export\":{\"individualBaseUri\":\"http://localhost:3000/display\"}}}" scholars/discovery
```

> The environment variable `SPRING_APPLICATION_JSON` will override properties in application.yml.

## Verify Installation

With the above installation instructions, the following service endpoints can be verified:

1. [HAL Explorer (9000/explorer)](http://localhost:9000)
2. [REST API (9000/individual)](http://localhost:9000/individual)
3. [REST API Docs (9000/api)](http://localhost:9000/api)
4. [GraphQL UI (9000/gui)](http://localhost:9000/gui)

The [HAL(Hypertext Application Language)](https://www.baeldung.com/spring-rest-hal) explorer can be used to browse scholars-discovery resources.

## Workarounds Waiting Dependency Patches

1. `spring-data-solr` dependency from TAMU Maven repository.
   - Added `tamu-releases` repository in pom.xml
   - Added dependency `4.1.6.TAMU.RELEASE` version of `spring-data-solr` in pom.xml
   - Excluded `spring-data-solr` from `spring-boot-starter-data-solr` dependency

   > Waiting on https://jira.spring.io/browse/DATASOLR-572

2. Using custom query and query parser to add edismax/dismax query parameters.
   - Added package `edu.tamu.scholars.middleware.discovery.query`
   - Registered parsers on `@PostConstruct` of `IndividualRepoImpl`

   > Waiting on https://jira.spring.io/browse/DATASOLR-153

