package com.example.backend.enums;

import lombok.Getter;

@Getter
public enum CategoryTypeEnum {
    게시판("POST", "게시판"),
    일기("DIARY","일기");

    private final String code;
    private final String name;

    CategoryTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}