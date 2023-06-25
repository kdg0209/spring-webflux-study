package com.webfluxstudy.application.reactor.reactor.repository;

import com.webfluxstudy.application.reactor.common.entity.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public class UserReactorRepository {

    private final Map<String, UserEntity> userEntityMap;

    public UserReactorRepository() {
        UserEntity user = new UserEntity("1234", "KDG", 29, "image#1000");
        this.userEntityMap = Map.of("1234", user);
    }

    @SneakyThrows
    public Mono<UserEntity> findById(String userId) {
        return Mono.create(sink -> {
            log.info("UserRepository.findById: {}", userId);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {}

            UserEntity userEntity = userEntityMap.get(userId);
            if (userEntity == null) {
                sink.success();
            } else {
                sink.success(userEntity);
            }
        });
    }
}
