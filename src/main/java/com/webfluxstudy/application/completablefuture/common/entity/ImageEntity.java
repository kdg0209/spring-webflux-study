package com.webfluxstudy.application.completablefuture.common.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageEntity {

    private final String id;
    private final String name;
    private final String url;
}
