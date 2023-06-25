package com.webfluxstudy.application.webfluxexample;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@Slf4j
public class ContextExample {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @SneakyThrows
    public static void main(String[] args) {
        threadLocal.set("wooman");

//        Flux.create(sink -> {
//            log.info("ThreadLocal: {}", threadLocal.get());
//            sink.next(1);
//        })
//        .publishOn(Schedulers.parallel())
//        .map(value -> {
//            log.info("ThreadLocal: {}", threadLocal.get());
//            return value;
//        })
//        .publishOn(Schedulers.boundedElastic())
//        .map(value -> {
//            log.info("ThreadLocal: {}", threadLocal.get());
//            return value;
//        })
//        .subscribeOn(Schedulers.single())
//        .subscribe(
//            value -> log.info("value: {}", value)
//        );

//        System.out.println();
//        System.out.println("Context write");
//
        Context initialContext = Context.of("name", "wooman");
//
//        Flux.create(sink -> {
//           Object name = sink.contextView().get("name");
//           log.info("name in create: {}", name);
//           sink.next(1);
//        })
//       .contextWrite(context -> context.put("name", "KDG"))
//       .subscribe(
//               value -> log.info("value: {}", value),
//               error -> log.info("error: {}", error),
//               () -> log.info("complete"),
//               initialContext
//       );

        System.out.println();
        System.out.println("Mono deferContextual");

        Mono.just(1)
                .flatMap(value -> Mono.deferContextual(contextView -> {
                    Object name = contextView.get("name");
                    log.info("name: {}", name);
                    return Mono.just(value + ", " +name);
                }))
                .contextWrite(context -> context.put("name", "KDG"))
                .subscribe(
                        value -> log.info("value: {}", value),
                        error -> log.info("error: {}", error),
                        (() -> log.info("complete")),
                        initialContext
                );

        Thread.sleep(1000);
    }
}
