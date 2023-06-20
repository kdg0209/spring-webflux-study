package com.webfluxstudy.application.completablefuture.blocking;

import com.webfluxstudy.application.completablefuture.blocking.repository.ArticleRepository;
import com.webfluxstudy.application.completablefuture.blocking.repository.FollowRepository;
import com.webfluxstudy.application.completablefuture.blocking.repository.ImageRepository;
import com.webfluxstudy.application.completablefuture.blocking.repository.UserRepository;
import com.webfluxstudy.application.completablefuture.common.Article;
import com.webfluxstudy.application.completablefuture.common.Image;
import com.webfluxstudy.application.completablefuture.common.User;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserBlockingService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;
    private final FollowRepository followRepository;

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id)
                .map(userEntity -> {
                    Optional<Image> image = imageRepository.findById(userEntity.getProfileImageId())
                            .map(imageEntity -> new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()));

                    List<Article> articleList = articleRepository.findAllByUserId(userEntity.getId()).stream()
                            .map(articleEntity -> new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent()))
                            .collect(Collectors.toList());

                    Long followCount = followRepository.countByUserId(userEntity.getId());

                    return new User(
                            userEntity.getId(),
                            userEntity.getName(),
                            userEntity.getAge(),
                            image,
                            articleList,
                            followCount
                    );
                });
    }
}
