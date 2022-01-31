# spring-webflux-demo

## Installation

## Usage

### Configuration

``` shell
$ export WEBFLUX_dbUrl=<YOUR_db_connection_string>
```

### Run

``` shell
$ mvn clean spring-boot:run
```

### Demo

* HTTP POST http://localhost:8080/create
* HTTP PUT http://localhost:8080/update
* HTTP GET http://localhost:8080/
* `$ curl -v -X POST -H "Content-Type: application/json" -d '{"id":1, "name":"user_1", "salary":101}' http://localhost:8080/create`

ref. [https://howtodoinjava.com/spring-webflux/spring-webflux-tutorial/](https://howtodoinjava.com/spring-webflux/spring-webflux-tutorial/)

## hello

- gs-reactive-rest-service