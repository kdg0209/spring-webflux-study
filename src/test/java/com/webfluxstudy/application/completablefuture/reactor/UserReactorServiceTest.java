package com.webfluxstudy.application.completablefuture.reactor;

import com.webfluxstudy.application.reactor.common.User;
import com.webfluxstudy.application.reactor.reactor.UserReactorService;
import com.webfluxstudy.application.reactor.reactor.repository.ArticleReactorRepository;
import com.webfluxstudy.application.reactor.reactor.repository.FollowReactorRepository;
import com.webfluxstudy.application.reactor.reactor.repository.ImageReactorRepository;
import com.webfluxstudy.application.reactor.reactor.repository.UserReactorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class UserReactorServiceTest {

    private UserReactorService userReactorService;
    private UserReactorRepository userReactorRepository;
    private ArticleReactorRepository articleReactorRepository;
    private ImageReactorRepository imageReactorRepository;
    private FollowReactorRepository followReactorRepository;

    @BeforeEach
    void setUp() {
        userReactorRepository = new UserReactorRepository();
        articleReactorRepository = new ArticleReactorRepository();
        imageReactorRepository = new ImageReactorRepository();
        followReactorRepository = new FollowReactorRepository();

        userReactorService = new UserReactorService(articleReactorRepository, followReactorRepository, imageReactorRepository, userReactorRepository);
    }

    @Test
    void getUserEmptyIfInvalidUserIdIsGiven() throws ExecutionException, InterruptedException {
        // given
        String userId = "invalid_user_id";

        // when
        Optional<User> user = userReactorService.findUserById(userId).blockOptional();

        // then
        assertThat(user.isEmpty()).isTrue();
    }

    @Test
    void testGetUser() throws ExecutionException, InterruptedException {
        // given
        String userId = "1234";

        // when
        Optional<User> optionalUser = userReactorService.findUserById(userId).blockOptional();

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