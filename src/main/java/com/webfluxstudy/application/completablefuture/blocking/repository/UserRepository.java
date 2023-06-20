package com.webfluxstudy.application.completablefuture.blocking.repository;

import com.webfluxstudy.application.completablefuture.common.entity.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class UserRepository {

    private final Map<String, UserEntity> userEntityMap;

    public UserRepository() {
        UserEntity user = new UserEntity("1234", "KDG", 29, "image#1000");
        this.userEntityMap = Map.of("1234", user);
    }

    @SneakyThrows
    public Optional<UserEntity> findById(String userId) {
        log.info("UserRepository.findById: {}", userId);
        Thread.sleep(1000L);
        UserEntity userEntity = userEntityMap.get(userId);

        return Optional.ofNullable(userEntity);
    }
}
