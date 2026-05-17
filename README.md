# Backpressure

This project exists as an example implementation of handling backpressure when working wih Reactive Streams. It is using the Spring Webflux implementation of Reactive Streams.

## Project Structure

This is a multi-module Gradle project. Each module is a separate application. There is a `producer` module which produces 100 events with a delay of 100ms between each message. There is also a `dropping-consumer` module which will consume messages produced by the `producer`. The intention is to add multiple consumer modules with different implementations of handling backpressure. Only one exists for now.

## Running locally

### Pre-requisites
- Java 25

1. Start the producer `./gradlew :producer:bootRun`
2. Start the consumer `./gradlew :dropping-consumer:bootRun`