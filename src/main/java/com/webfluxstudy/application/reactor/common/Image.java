package com.webfluxstudy.application.reactor.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Image {

    private final String id;
    private final String name;
    private final String url;
}
