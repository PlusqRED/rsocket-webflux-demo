package com.grape.producer.controller;

import com.grape.producer.model.GreetingRequest;
import com.grape.producer.model.GreetingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;

@Controller
public class ProducerController {
    private Logger logger = LoggerFactory.getLogger(ProducerController.class);

    @MessageMapping("greet")
    public Mono<GreetingResponse> greet(GreetingRequest request) {
        return Mono.just(new GreetingResponse("Hello " + request.getName() + " @ " + Instant.now()));
    }

    @MessageMapping("greet-stream")
    public Flux<GreetingResponse> greetStream(GreetingRequest request) {
        return Flux.generate(greetingResponseSynchronousSink -> {
                    logger.info("Response has produced!");
                    greetingResponseSynchronousSink.next(new GreetingResponse(request.getName() + "@" + LocalDateTime.now().toString()));
                }
        );
    }
}
