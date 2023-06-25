package com.webfluxstudy.application.reactor.reactor;

import com.webfluxstudy.application.reactor.common.Article;
import com.webfluxstudy.application.reactor.common.EmptyImage;
import com.webfluxstudy.application.reactor.common.Image;
import com.webfluxstudy.application.reactor.common.User;
import com.webfluxstudy.application.reactor.common.entity.UserEntity;
import com.webfluxstudy.application.reactor.reactor.repository.ArticleReactorRepository;
import com.webfluxstudy.application.reactor.reactor.repository.FollowReactorRepository;
import com.webfluxstudy.application.reactor.reactor.repository.ImageReactorRepository;
import com.webfluxstudy.application.reactor.reactor.repository.UserReactorRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserReactorService {

    private final ArticleReactorRepository articleReactorRepository;
    private final FollowReactorRepository followReactorRepository;
    private final ImageReactorRepository imageReactorRepository;
    private final UserReactorRepository userReactorRepository;

    @SneakyThrows
    public Mono<User> findUserById(String userId) {
        return userReactorRepository.findById(userId)
                .flatMap(this::findUser);
    }

    @SneakyThrows
    private Mono<User> findUser(UserEntity userEntity) {
        Context context = Context.of("user", userEntity);

        Mono<Image> imageMono = imageReactorRepository.findWithContext()
                .map(imageEntity -> new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()))
                .onErrorReturn(new EmptyImage())
                .contextWrite(context);

        Mono<List<Article>> articleMono = articleReactorRepository.findAllWithContext()
                .skip(5)
                .take(2)
                .map(articleEntity -> new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent()))
                .collectList()
                .contextWrite(context);

        Mono<Long> countMono = followReactorRepository.countWithContext()
                .contextWrite(context);

        return Flux.mergeSequential(imageMono, articleMono, countMono)
                .collectList()
                .map(result -> {
                    Image image = (Image) result.get(0);
                    List<Article> articles = (List<Article>) result.get(1);
                    Long followCount = (Long) result.get(2);

                    Optional<Image> imageOptional = Optional.empty();

                    if (!(image instanceof EmptyImage)) {
                        imageOptional = Optional.of(image);
                    }

                    return new User (
                            userEntity.getId(),
                            userEntity.getName(),
                            userEntity.getAge(),
                            imageOptional,
                            articles,
                            followCount
                    );
                });

//        return Mono.zip(imageMono, articleMono, countMono)
//                .map(result -> {
//                    Image image = result.getT1();
//                    List<Article> articles = result.getT2();
//                    Long followCount = result.getT3();
//
//                    Optional<Image> imageOptional = Optional.empty();
//
//                    if (!(image instanceof EmptyImage)) {
//                        imageOptional = Optional.of(image);
//                    }
//
//                    return new User (
//                            userEntity.getId(),
//                            userEntity.getName(),
//                            userEntity.getAge(),
//                            imageOptional,
//                            articles,
//                            followCount
//                    );
//                });
    }
}
