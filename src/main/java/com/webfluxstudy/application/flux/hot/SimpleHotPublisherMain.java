package com.webfluxstudy.application.flux.hot;

import com.webfluxstudy.application.flux.common.SimpleNamedSubscriber;
import lombok.SneakyThrows;

public class SimpleHotPublisherMain {

    @SneakyThrows
    public static void main(String[] args) {
        SimpleHotPublisher publisher = new SimpleHotPublisher();

        SimpleNamedSubscriber<Integer> subscriber1 = new SimpleNamedSubscriber<>("sub 1");
        publisher.subscribe(subscriber1);

        Thread.sleep(5000);
        subscriber1.cancel();

        SimpleNamedSubscriber<Integer> subscriber2 = new SimpleNamedSubscriber<>("sub 2");
        SimpleNamedSubscriber<Integer> subscriber3 = new SimpleNamedSubscriber<>("sub 3");

        publisher.subscribe(subscriber2);
        publisher.subscribe(subscriber3);

        Thread.sleep(1000);
        publisher.shutdown();
    }
}
