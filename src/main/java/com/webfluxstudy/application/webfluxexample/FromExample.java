package com.webfluxstudy.application.webfluxexample;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Slf4j
public class FromExample {

    public static void main(String[] args) {

        Mono.fromCallable(() -> 1)
                .subscribe(value -> log.info("value fromCallable : {}", value));

        Mono.fromFuture(CompletableFuture.supplyAsync(() -> 1))
                .subscribe(value -> log.info("value fromFuture : {}", value));

        Mono.fromSupplier(() -> 1)
                .subscribe(value -> log.info("value fromSupplier : {}", value));

        Mono.fromRunnable(() -> {})
                .subscribe(null, null, () -> log.info("value fromRunnable"));

        System.out.println("Flux start");

        Flux.fromIterable(List.of(1, 2, 3, 4, 5, 6))
                .subscribe(value -> log.info("value fromIterable : {}", value));

        Flux.fromStream(IntStream.rangeClosed(1, 10).boxed())
                .subscribe(value -> log.info("value fromStream : {}", value));

        Flux.fromArray(new Integer[]{1, 2, 3, 4, 5, 6})
                .subscribe(value -> log.info("value fromArray : {}", value));

        Flux.range(1, 10)
                .subscribe(value -> log.info("value range : {}", value));

        System.out.println();
        System.out.println("Flux generate");

        Flux.generate(() -> 4, (state, sink) -> {
            sink.next(state);
            if (state == 9) {
                sink.complete();
            }
            return state + 1;
        })
        .subscribe(
            value -> log.info("value: {}", value),
            error -> log.error("error: {}", error),
            () -> log.info("complete")
        );

        System.out.println();
        System.out.println("Flux create");

        Flux.create(sink -> {
            CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 5; i++) {
                    sink.next(i);
                }
            });

            CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
                for (int i = 0; i < 5; i++) {
                    sink.next(i);
                }
            });

            CompletableFuture.allOf(task1, task2).thenRunAsync(sink::complete);
        })
        .subscribe(
                value -> log.info("value: {}", value),
                error -> log.error("error: {}", error),
                () -> log.info("complete")
        );

        Flux.fromStream(IntStream.rangeClosed(1, 10).boxed())
                .handle((value, sink) -> {
                    if (value % 2 == 0) {
                        sink.next(value);
                    }
                })
                .subscribe(
                    value -> log.info("value: {}", value),
                    error -> log.error("error: {}", error),
                    () -> log.info("complete")
                );
    }
}
