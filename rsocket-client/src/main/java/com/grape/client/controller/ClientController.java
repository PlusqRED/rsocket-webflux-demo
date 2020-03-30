package com.grape.client.controller;

import com.grape.client.model.GreetingRequest;
import com.grape.client.model.GreetingResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
public class ClientController {
    private final RSocketRequester requester;
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @GetMapping("/greet/{name}")
    public Mono<GreetingResponse> greet(@PathVariable String name) {
        return requester
                .route("greet")
                .data(new GreetingRequest(name))
                .retrieveMono(GreetingResponse.class);
    }

    @GetMapping(value = "/greet-stream/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GreetingResponse> greetStream(@PathVariable String name) {
        return requester
                .route("greet-stream")
                .data(new GreetingRequest(name))
                .retrieveFlux(GreetingResponse.class)
                .doOnNext(greetingResponse -> logger.info(greetingResponse.getGreeting() + " has consumed!"))
                .delayElements(Duration.ofSeconds(3));
    }
}
