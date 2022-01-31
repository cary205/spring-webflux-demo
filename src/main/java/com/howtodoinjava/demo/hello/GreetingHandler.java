package com.howtodoinjava.demo.hello;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

	public Mono<ServerResponse> hello(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
			.body(BodyInserters.fromValue("Hello, Spring!"));
	}
	
	public Mono<ServerResponse> time(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
			.body(this.streamTime(), String.class);
	}
	
	public Flux<String> streamTime() {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		Flux<String> events = Flux.fromStream(Stream.generate(() -> new Date().toString()));
		return Flux.zip(events, interval, (key, value) -> key);
}
}
