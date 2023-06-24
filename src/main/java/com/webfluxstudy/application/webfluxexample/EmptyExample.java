package com.webfluxstudy.application.webfluxexample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class EmptyExample {

    public static void main(String[] args) {
        Mono.empty()
            .subscribe(
                value -> log.info("value: {}", value),
                error -> log.error("error: {}", error),
                () -> log.info("complete")
            );

        Flux.empty()
            .subscribe(
                    value -> log.info("value: {}", value),
                    error -> log.error("error: {}", error),
                    () -> log.info("complete")
            );
    }
}
