package com.fzcoder.opensource.blog.framework.annotation;

public enum IdType {
    NONE(0),

    AUTO(1);

    private final int key;

    IdType(int key) {
        this.key = key;
    }
}
