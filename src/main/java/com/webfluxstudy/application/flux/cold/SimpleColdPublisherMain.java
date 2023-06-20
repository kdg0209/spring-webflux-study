package com.webfluxstudy.application.flux.cold;

import com.webfluxstudy.application.flux.common.SimpleNamedSubscriber;
import lombok.SneakyThrows;

public class SimpleColdPublisherMain {

    @SneakyThrows
    public static void main(String[] args) {
        SimpleColdPublisher simpleColdPublisher = new SimpleColdPublisher();
        SimpleNamedSubscriber<Integer> subscribe1 = new SimpleNamedSubscriber<>("sub 1");

        simpleColdPublisher.subscribe(subscribe1);
        Thread.sleep(5000);

        SimpleNamedSubscriber<Integer> subscribe2 = new SimpleNamedSubscriber<>("sub 2");
        simpleColdPublisher.subscribe(subscribe2);
    }
}
