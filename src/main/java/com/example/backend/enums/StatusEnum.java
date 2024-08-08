package com.example.backend.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    CREATED("CREATED", "생성됨"),
    READED("READED", "조회됨"),
    UPDATEED("UPDATEED","수정됨"),
    DELETEED("DELETEED","삭제됨");

    private final String code;
    private final String name;

    StatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}