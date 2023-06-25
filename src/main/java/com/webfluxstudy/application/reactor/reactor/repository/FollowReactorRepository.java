package com.webfluxstudy.application.reactor.reactor.repository;

import com.webfluxstudy.application.reactor.common.entity.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class FollowReactorRepository {

    private final Map<String , Long> userFollowCountMap;

    public FollowReactorRepository() {
        this.userFollowCountMap =  Map.of("1234", 1000L);
    }

    @SneakyThrows
    public Mono<Long> countByUserId(String userId) {
        return Mono.justOrEmpty(userFollowCountMap.getOrDefault(userId, 0L));
    }

    public Mono<Long> countWithContext() {
        return Mono.deferContextual(contextView -> {
            Optional<UserEntity> optional = contextView.getOrEmpty("user");

            if (optional.isEmpty()) {
                throw new RuntimeException("user not found");
            }
            return Mono.just(optional.get().getId());
        })
        .flatMap(this::countByUserId);
    }
}
