package com.webfluxstudy.application.reactor.future.repository;

import com.webfluxstudy.application.completablefuture.common.entity.ArticleEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class ArticleFutureRepository {

    private static List<ArticleEntity> articleEntities;

    public ArticleFutureRepository() {
        articleEntities = List.of(
                new ArticleEntity("1", "기사 1", "내용1", "1234"),
                new ArticleEntity("2", "기사 2", "내용2", "1234"),
                new ArticleEntity("3", "기사 3", "내용3", "10000")
        );
    }

    @SneakyThrows
    public CompletableFuture<List<ArticleEntity>> findAllByUserId(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("ArticleRepository.findAllByUserId: {}", userId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            return articleEntities.stream()
                    .filter(articleEntity -> articleEntity.getUserId().equals(userId))
                    .collect(Collectors.toList());
        });
    }
}
