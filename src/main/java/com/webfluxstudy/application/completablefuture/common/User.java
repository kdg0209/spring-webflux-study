package com.webfluxstudy.application.completablefuture.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class User {

    private final String id;
    private final String name;
    private final int age;
    private final Optional<Image> profileImage;
    private final List<Article> articleList;
    private final Long followCount;
}
