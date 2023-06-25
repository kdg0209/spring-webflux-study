package com.webfluxstudy.application.webfluxexample;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class MapExample {

    @SneakyThrows
    public static void main(String[] args) {

        Flux.range(1,5)
                .flatMap(value -> Flux.range(1,2)
                        .map(value2 -> value + ", " + value2)
                        .publishOn(Schedulers.parallel())
                )
                .doOnNext(value -> log.info("doOnNext: {}", value))
                .subscribe();

        System.out.println();
        System.out.println("Flux.take");

        Flux.range(1, 10)
                .take(5)
                .doOnNext(value -> log.info("doOnNext: {}", value))
                .subscribe();

        System.out.println();
        System.out.println("Flux.takeLast");

        Flux.range(1, 10)
                .takeLast(5)
                .doOnNext(value -> log.info("doOnNext: {}", value))
                .subscribe();


        System.out.println();
        System.out.println("Flux.skip");

        Flux.range(1, 10)
                .skip(5)
                .doOnNext(value -> log.info("doOnNext: {}", value))
                .subscribe();

        System.out.println();
        System.out.println("Flux.skipLast");

        Flux.range(1, 10)
                .skipLast(5)
                .doOnNext(value -> log.info("doOnNext: {}", value))
                .subscribe();

        System.out.println();
        System.out.println("Flux.collectList");

        Flux.range(1, 10)
                .collectList()
                .doOnNext(value -> log.info("doOnNext: {}", value))
                .subscribe();

        Thread.sleep(1000);
    }
}
