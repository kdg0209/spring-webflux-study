package com.webfluxstudy.application.reactor.future.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class FollowFutureRepository {

    private final Map<String , Long> userFollowCountMap;

    public FollowFutureRepository() {
        this.userFollowCountMap =  Map.of("1234", 1000L);
    }

    @SneakyThrows
    public CompletableFuture<Long> countByUserId(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("FollowRepository.countByUserId: {}", userId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            return userFollowCountMap.getOrDefault(userId, 0L);
        });
    }
}
