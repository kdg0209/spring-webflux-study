package com.webfluxstudy.application.webfluxexample;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class DelayExample {

    @SneakyThrows
    public static void main(String[] args) {

//        Flux.create(sink -> {
//            for (int i = 0; i <= 5; i++) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                sink.next(i);
//            }
//            sink.complete();
//        })
//        .delayElements(Duration.ofMillis(500))
//        .doOnNext(item -> log.info("item: {}", item))
//        .subscribeOn(Schedulers.single())
//        .subscribe(
//                value -> log.info("value: {}", value),
//                error -> log.error("error: {}", error),
//                () -> log.info("complete")
//        );

//        System.out.println();
//        System.out.println("Flux concat");
//
//        Flux<Integer> concatFlux1 = Flux.range(1, 3)
//                .doOnSubscribe(value -> log.info("doOnSubscribe1"))
//                .delayElements(Duration.ofMillis(100));
//
//        Flux<Integer> concatFlux2 = Flux.range(10, 3)
//                .doOnSubscribe(value -> log.info("doOnSubscribe2"))
//                .delayElements(Duration.ofMillis(100));
//
//        Flux.concat(concatFlux1, concatFlux2)
//                .subscribe(
//                        value -> log.info("value: {}", value)
//                );

//        System.out.println();
//        System.out.println("Flux merge");
//
//        Flux<Integer> mergeFlux1 = Flux.range(1, 3)
//                .doOnSubscribe(value -> log.info("doOnSubscribe1"))
//                .delayElements(Duration.ofMillis(100));
//
//        Flux<Integer> mergeFlux2 = Flux.range(10, 3)
//                .doOnSubscribe(value -> log.info("doOnSubscribe2"))
//                .delayElements(Duration.ofMillis(100));
//
//        Flux.merge(mergeFlux1, mergeFlux2)
//                .subscribe(
//                        value -> log.info("value: {}", value)
//                );

        System.out.println();
        System.out.println("Flux mergeSequential");

        Flux<Integer> mergeSequentialFlux1 = Flux.range(1, 3)
                .doOnSubscribe(value -> log.info("doOnSubscribe1"))
                .delayElements(Duration.ofMillis(100));

        Flux<Integer> mergeSequentialFlux2 = Flux.range(10, 3)
                .doOnSubscribe(value -> log.info("doOnSubscribe2"))
                .delayElements(Duration.ofMillis(100));

        Flux.mergeSequential(mergeSequentialFlux1, mergeSequentialFlux2)
                .subscribe(
                    value -> log.info("value: {}", value)
                );

        Thread.sleep(6000);
    }
}
