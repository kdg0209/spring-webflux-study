package com.webfluxstudy.application.reactor.common.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArticleEntity {

    private final String id;
    private final String title;
    private final String content;
    private final String userId;
}
