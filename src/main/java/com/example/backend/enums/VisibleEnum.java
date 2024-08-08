package com.example.backend.enums;

import lombok.Getter;

@Getter
public enum VisibleEnum {
    PUBLISHED("PUBLISHED", "공개됨"),
    UNPUBLISHED("UNPUBLISHED","비공개됨");

    private final String code;
    private final String name;

    VisibleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}