package com.webfluxstudy.application.completablefuture.future;

import com.webfluxstudy.application.completablefuture.common.User;
import com.webfluxstudy.application.completablefuture.future.UserFutureService;
import com.webfluxstudy.application.completablefuture.future.repository.ArticleFutureRepository;
import com.webfluxstudy.application.completablefuture.future.repository.FollowFutureRepository;
import com.webfluxstudy.application.completablefuture.future.repository.ImageFutureRepository;
import com.webfluxstudy.application.completablefuture.future.repository.UserFutureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserFutureServiceTest {

    private UserFutureService userFutureService;
    private UserFutureRepository userRepository;
    private ArticleFutureRepository articleRepository;
    private ImageFutureRepository imageRepository;
    private FollowFutureRepository followRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserFutureRepository();
        articleRepository = new ArticleFutureRepository();
        imageRepository = new ImageFutureRepository();
        followRepository = new FollowFutureRepository();

        userFutureService = new UserFutureService(userRepository, articleRepository, imageRepository, followRepository);
    }

    @Test
    void getUserEmptyIfInvalidUserIdIsGiven() throws ExecutionException, InterruptedException {
        // given
        String userId = "invalid_user_id";

        // when
        Optional<User> user = userFutureService.findUserById(userId).get();

        // then
        assertThat(user.isEmpty()).isTrue();
    }

    @Test
    void testGetUser() throws ExecutionException, InterruptedException {
        // given
        String userId = "1234";

        // when
        Optional<User> optionalUser = userFutureService.findUserById(userId).get();

        // then
        assertThat(optionalUser.isEmpty()).isFalse();
        var user = optionalUser.get();
        assertThat(user.getName()).isEqualTo("KDG");
        assertThat(user.getAge()).isEqualTo(29);

        assertThat(user.getProfileImage().isEmpty()).isFalse();
        var image = user.getProfileImage().get();
        assertThat(image.getId()).isEqualTo( "image#1000");
        assertThat(image.getName()).isEqualTo( "profileImage");
        assertThat(image.getUrl()).isEqualTo( "https://dailyone.com/images/1000");

        assertThat(user.getArticleList().size()).isEqualTo(2);
        assertThat(user.getFollowCount()).isEqualTo(1000);
    }
}
