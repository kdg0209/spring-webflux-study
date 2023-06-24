package com.webfluxstudy.application.webfluxexample;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class ErrorHandlingExample {

    @SneakyThrows
    public static void main(String[] args) {
//        Flux.create(sink -> {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            sink.error(new RuntimeException("error"));
//        })
//        .subscribe(
//            value -> log.info("value: {}", value),
//            error -> log.info("error: {}", error)
//        );

        System.out.println();
        System.out.println("Flux.onErrorReturn");

        Flux.error(new RuntimeException("error"))
                .onErrorReturn(0)
                .subscribe(
                    value -> log.info("value: {}", value),
                    error -> log.info("error: {}", error)
                );

        Flux.just(List.of(1, 2, 3))
                .onErrorReturn(shouldDoOnError()) // 무조건 실행됨
                .subscribe(
                        value -> log.info("value: {}", value),
                        error -> log.info("error: {}", error)
                );

        System.out.println();
        System.out.println("Flux.onErrorResume");

        Flux.just(List.of(1, 2, 3))
                .handle((value, sink) -> {
                    sink.error(new RuntimeException("error"));
                })
                .onErrorResume(ErrorHandlingExample::onErrorResume)
                .subscribe(
                        value -> log.info("value: {}", value),
                        error -> log.info("error: {}", error),
                        () -> log.info("complete")
                );

        Flux.error(new RuntimeException("error"))
                .onErrorResume(ErrorHandlingExample::onErrorResume)
                .onErrorComplete()
                .subscribe(
                        value -> log.info("value: {}", value),
                        error -> log.info("error: {}", error),
                        () -> log.info("complete")
                );
    }

    private static List<Integer> shouldDoOnError() {
        log.error("shouldDoOnError Call");
        return List.of(-100);
    }

    private static Flux onErrorResume(Throwable throwable) {
        log.error("onErrorResume Call");
        return Flux.just(-1,- 2,- 3);
    }
}
