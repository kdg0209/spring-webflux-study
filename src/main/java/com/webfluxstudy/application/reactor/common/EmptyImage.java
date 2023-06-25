package com.webfluxstudy.application.reactor.common;

public class EmptyImage extends Image {

    public EmptyImage() {
        super("", "", "");
        System.out.println("error default");
    }
}