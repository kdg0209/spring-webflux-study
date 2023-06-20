package com.webfluxstudy.application.completablefuture.future.repository;

import com.webfluxstudy.application.completablefuture.common.entity.ImageEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class ImageFutureRepository {

    private final Map<String, ImageEntity> imageEntityMap;

    public ImageFutureRepository() {
        imageEntityMap = Map.of(
                "image#1000", new ImageEntity("image#1000", "profileImage", "https://dailyone.com/images/1000")
        );
    }

    @SneakyThrows
    public CompletableFuture<Optional<ImageEntity>> findById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("ImageRepository.findById: {}", id);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            return Optional.ofNullable(imageEntityMap.get(id));
        });
    }
}
