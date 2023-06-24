package com.webfluxstudy.application.webfluxexample;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerExample {

    @SneakyThrows
    public static void main(String[] args) {

//        System.out.println();
//        System.out.println("Schedulers.immediate start");
//
//        Flux.create(sink -> {
//            for (int i = 0; i <= 5; i++) {
//                log.info("next: {}", i);
//                sink.next(i);
//            }
//        })
//        .subscribeOn(Schedulers.immediate())
//        .subscribe(
//                value -> log.info("value: {}", value),
//                error -> log.info("error: {}", error),
//                () -> log.info("complete")
//        );
//
//        System.out.println();
//        System.out.println("Schedulers.single start");
//
//        for (int i = 0; i < 100; i++) {
//            final  int idx = i;
//            Flux.create(sink -> {
//                log.info("next: {}", idx);
//                sink.next(idx);
//            })
//            .subscribeOn(Schedulers.single())
//            .subscribe(
//                    value -> log.info("value: {}", value),
//                    error -> log.info("error: {}", error),
//                    () -> log.info("complete")
//            );
//        }
//
//        System.out.println();
//        System.out.println("Schedulers.parallel start");
//
//        for (int i = 0; i < 100; i++) {
//            final  int idx = i;
//            Flux.create(sink -> {
//                        log.info("next: {}", idx);
//                        sink.next(idx);
//                    })
//                    .subscribeOn(Schedulers.parallel())
//                    .subscribe(
//                            value -> log.info("value: {}", value),
//                            error -> log.info("error: {}", error),
//                            () -> log.info("complete")
//                    );
//        }

//        System.out.println();
//        System.out.println("Schedulers.boundedElastic start");
//
//        for (int i = 0; i < 100; i++) {
//            final  int idx = i;
//            Flux.create(sink -> {
//                        log.info("next: {}", idx);
//                        sink.next(idx);
//                    })
//                    .subscribeOn(Schedulers.boundedElastic())
//                    .subscribe(
//                            value -> log.info("value: {}", value),
//                            error -> log.info("error: {}", error),
//                            () -> log.info("complete")
//                    );
//        }

//        System.out.println();
//        System.out.println("Schedulers.newSingle start");
//
//        for (int i = 0; i < 100; i++) {
//            final int idx = i;
//            Scheduler single = Schedulers.newSingle("KDG");
//            Flux.create(sink -> {
//                        log.info("next: {}", idx);
//                        sink.next(idx);
//                    })
//                    .subscribeOn(single)
//                    .subscribe(
//                        value -> {log.info("value: {}", value); single.dispose();},
//                        error -> log.info("error: {}", error),
//                        () -> log.info("complete")
//                    );
//        }

        System.out.println();
        System.out.println("Schedulers.publishOn start");

        Flux.create(sink -> {
            for (int i = 0; i < 5; i++) {
                log.info("next: {}", i);
                sink.next(i);
            }
        })
        .publishOn(Schedulers.single())
        .doOnNext(item -> log.info("doOnNext1: {}", item))
        .subscribeOn(Schedulers.parallel())
        .publishOn(Schedulers.boundedElastic())
        .doOnNext(item -> log.info("doOnNext2: {}", item))
        .subscribe(
                value -> log.info("value: {}", value),
                error -> log.info("error: {}", error),
                () -> log.info("complete")
        );

        Thread.sleep(1000);
    }
}
