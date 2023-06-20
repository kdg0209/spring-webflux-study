package com.webfluxstudy.application.completablefuture.blocking;

import com.webfluxstudy.application.completablefuture.blocking.UserBlockingService;
import com.webfluxstudy.application.completablefuture.blocking.repository.ArticleRepository;
import com.webfluxstudy.application.completablefuture.blocking.repository.FollowRepository;
import com.webfluxstudy.application.completablefuture.blocking.repository.ImageRepository;
import com.webfluxstudy.application.completablefuture.blocking.repository.UserRepository;
import com.webfluxstudy.application.completablefuture.common.Image;
import com.webfluxstudy.application.completablefuture.common.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserBlockingServiceBlockingTest {

    private UserBlockingService userBlockingService;
    private UserRepository userRepository;
    private ArticleRepository articleRepository;
    private ImageRepository imageRepository;
    private FollowRepository followRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        articleRepository = new ArticleRepository();
        imageRepository = new ImageRepository();
        followRepository = new FollowRepository();

        userBlockingService = new UserBlockingService(userRepository, articleRepository, imageRepository, followRepository);
    }

    @Test
    void getUserEmptyIfInvalidUserIdIsGiven() {
        // given
        String userId = "invalid_user_id";

        // when
        Optional<User> user = userBlockingService.findUserById(userId);

        // then
        assertThat(user.isEmpty()).isTrue();
    }

    @Test
    void testGetUser() {
        // given
        String userId = "1234";

        // when
        Optional<User> optionalUser = userBlockingService.findUserById(userId);

        // then
        assertThat(optionalUser.isEmpty()).isFalse();
        User user = optionalUser.get();
        assertThat(user.getName()).isEqualTo( "KDG");
        assertThat(user.getAge()).isEqualTo( 29);

        assertThat(user.getProfileImage().isEmpty()).isFalse();
        Image image = user.getProfileImage().get();
        assertThat(image.getId()).isEqualTo( "image#1000");
        assertThat(image.getName()).isEqualTo( "profileImage");
        assertThat(image.getUrl()).isEqualTo( "https://dailyone.com/images/1000");

        assertThat(user.getArticleList().size()).isEqualTo(2);
        assertThat(user.getFollowCount()).isEqualTo(1000);
    }
}
