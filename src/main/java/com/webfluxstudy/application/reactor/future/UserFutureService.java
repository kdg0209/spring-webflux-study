package com.webfluxstudy.application.reactor.future;

import com.webfluxstudy.application.completablefuture.common.Article;
import com.webfluxstudy.application.completablefuture.common.Image;
import com.webfluxstudy.application.completablefuture.common.User;
import com.webfluxstudy.application.completablefuture.common.entity.UserEntity;
import com.webfluxstudy.application.completablefuture.future.repository.ArticleFutureRepository;
import com.webfluxstudy.application.completablefuture.future.repository.FollowFutureRepository;
import com.webfluxstudy.application.completablefuture.future.repository.ImageFutureRepository;
import com.webfluxstudy.application.completablefuture.future.repository.UserFutureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UserFutureService {

    private final UserFutureRepository userRepository;
    private final ArticleFutureRepository articleRepository;
    private final ImageFutureRepository imageRepository;
    private final FollowFutureRepository followRepository;

    public CompletableFuture<Optional<User>> findUserById(String userId) {
        return userRepository.findById(userId)
                .thenComposeAsync(this::findUser);
    }

    private CompletableFuture<Optional<User>> findUser(Optional<UserEntity> userEntityOptional) {
        if (userEntityOptional.isEmpty()) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        UserEntity userEntity = new UserEntity("1234", "KDG", 29, "image#1000");
//        UserEntity userEntity = userEntityOptional.get();

        CompletableFuture<Optional<Image>> imageFuture = imageRepository.findById(userEntity.getProfileImageId())
                .thenApplyAsync(imageEntityOptional -> imageEntityOptional.map(imageEntity -> new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl())));

        CompletableFuture<List<Article>> articlesFuture = articleRepository.findAllByUserId(userEntity.getId())
                .thenApplyAsync(articleEntities -> articleEntities.stream()
                        .map(articleEntity -> new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent()))
                        .collect(Collectors.toList())
                );

        CompletableFuture<Long> followCountFuture = followRepository.countByUserId(userEntity.getId());

        return CompletableFuture.allOf(imageFuture, articlesFuture, followCountFuture)
                .thenAcceptAsync(result -> {
                    log.info("Three futures are completed");
                })
                .thenRunAsync(() -> {
                    log.info("Three futures are also completed");
                })
                .thenApplyAsync(result -> {
                    Optional<Image> image = imageFuture.join();
                    List<Article> articles = articlesFuture.join();
                    Long followCount = followCountFuture.join();

                    return Optional.of(
                            new User(
                                    userEntity.getId(),
                                    userEntity.getName(),
                                    userEntity.getAge(),
                                    image,
                                    articles,
                                    followCount
                            )
                    );
                });
    }
}
