package com.grape.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class ClientConfiguration {

    @Bean
    RSocketRequester requester(RSocketStrategies strategies) {
        return RSocketRequester.builder()
                .rsocketStrategies(strategies)
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .connectTcp("localhost", 7000)
                .block();
    }
}
