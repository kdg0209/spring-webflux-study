package com.webfluxstudy.application.reactor.reactor.repository;

import com.webfluxstudy.application.reactor.common.entity.ImageEntity;
import com.webfluxstudy.application.reactor.common.entity.UserEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class ImageReactorRepository {

    private final Map<String, ImageEntity> imageEntityMap;

    public ImageReactorRepository() {
        imageEntityMap = Map.of(
                "image#1000", new ImageEntity("image#1000", "profileImage", "https://dailyone.com/images/1000")
        );
    }

    @SneakyThrows
    public Mono<ImageEntity> findById(String id) {
        return Mono.create(sink -> {
            log.info("ImageRepository.findById: {}", id);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            ImageEntity imageEntity = imageEntityMap.get(id);

            if (imageEntity == null) {
                sink.error(new RuntimeException("image not found"));
            } else {
                sink.success(imageEntity);
            }
        });
    }

    public Mono<ImageEntity> findWithContext() {
        return Mono.deferContextual(contextView -> {
            Optional<UserEntity> optional = contextView.getOrEmpty("user");
            if (optional.isEmpty()) throw new RuntimeException("user not found");

            return Mono.just(optional.get().getProfileImageId());
        })
        .flatMap(this::findById);
    }
}
