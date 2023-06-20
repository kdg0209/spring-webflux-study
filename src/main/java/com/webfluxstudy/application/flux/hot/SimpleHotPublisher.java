package com.webfluxstudy.application.flux.hot;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class SimpleHotPublisher implements Flow.Publisher<Integer> {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Future<Void> task;
    private List<Integer> numbers = new ArrayList<>();
    private List<SimpleHotSubscription> subscriptions = new ArrayList<>();

    public SimpleHotPublisher() {
        numbers.add(1);
        this.task = executorService.submit(() -> {
            for(int i = 2;; i++) {
                numbers.add(i);
                this.subscriptions.forEach(SimpleHotSubscription::wakeup);
                Thread.sleep(100);
            }
        });
    }

    public void shutdown() {
        this.task.cancel(true);
        this.executorService.shutdown();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        SimpleHotSubscription subscription = new SimpleHotSubscription(numbers.size(), subscriber);
        this.subscriptions.add(subscription);
        subscriber.onSubscribe(subscription);
    }

    private class SimpleHotSubscription implements Flow.Subscription {

        private int offset;
        private int requiredOffset;
        private final Flow.Subscriber<? super Integer> subscriber;
        private final ExecutorService subscriptionExecutorService = Executors.newSingleThreadExecutor();

        public SimpleHotSubscription(int lastElementIndex, Flow.Subscriber<? super Integer> subscriber) {
            this.offset = lastElementIndex - 1;
            this.requiredOffset = lastElementIndex - 1;
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            this.requiredOffset += n;
            this.onNextWhilePossible();
        }

        @Override
        public void cancel() {
            this.subscriber.onComplete();
            if (subscriptions.contains(this)) {
                subscriptions.remove(this);
            }
            subscriptionExecutorService.shutdown();
        }

        public void wakeup() {
            this.onNextWhilePossible();
        }

        private void onNextWhilePossible() {
            this.subscriptionExecutorService.submit(() -> {
                while (offset < requiredOffset && offset < numbers.size()) {
                    Integer item = numbers.get(offset);
                    this.subscriber.onNext(item);
                    this.offset++;
                }
            });
        }
    }
}
