# Order Service

## Description

Based on reactive web stack with Spring WebFlux (embedded Netty).
Reactive database driver for NoSQL database MongoDB.

The application has two services:
- order service manages orders and persists them in a mongo db database
- payment service manages payments and validate the payments submitted by reading and writing to kafka topics

The application front end is implemented with React.

## Configuration



## Tests

Embedded MongoDB for integration tests with flapdoodle library
- Adding standard mongodb dependency for tests necessary for legacy support of MongoTemplate in tests
- Add compiler option: --illegal-access=permit (until flapdoodle library updates)

