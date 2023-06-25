package com.webfluxstudy.application.reactor.common.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserEntity {

    private final String id;
    private final String name;
    private final int age;
    private final String profileImageId;
}
