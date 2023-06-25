package com.webfluxstudy.application.reactor.future.repository;

import com.webfluxstudy.application.completablefuture.common.entity.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class UserFutureRepository {

    private final Map<String, UserEntity> userEntityMap;

    public UserFutureRepository() {
        UserEntity user = new UserEntity("1234", "KDG", 29, "image#1000");
        this.userEntityMap = Map.of("1234", user);
    }

    @SneakyThrows
    public CompletableFuture<Optional<UserEntity>> findById(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("UserRepository.findById: {}", userId);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {}

            UserEntity userEntity = userEntityMap.get(userId);
            return Optional.ofNullable(userEntity);
        });
    }
}
