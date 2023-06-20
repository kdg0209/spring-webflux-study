package com.webfluxstudy.application.completablefuture.blocking.repository;

import com.webfluxstudy.application.completablefuture.common.entity.ImageEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class ImageRepository {

    private final Map<String, ImageEntity> imageEntityMap;

    public ImageRepository() {
        imageEntityMap = Map.of(
                "image#1000", new ImageEntity("image#1000", "profileImage", "https://dailyone.com/images/1000")
        );
    }

    @SneakyThrows
    public Optional<ImageEntity> findById(String id) {
        log.info("ImageRepository.findById: {}", id);
        Thread.sleep(1000);
        return Optional.ofNullable(imageEntityMap.get(id));
    }
}
