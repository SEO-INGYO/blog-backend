package com.example.backend.utils;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.example.backend.post.entity.PostTag;

public class StringUtils {

    // List<String>을 ,로 구분된 하나의 문자열로 변환하는 유틸리티 메서드
    public static String joinWithComma(List<String> list) {
        StringJoiner joiner = new StringJoiner(",");
        for (String item : list) {
            joiner.add(item);
        }
        return joiner.toString();
    }

    // List<PostTag>를 받아 각 PostTag의 Tag 이름을 ','로 구분된 문자열로 변환하는 메서드
    public static String joinTagNamesWithComma(List<PostTag> postTags) {
        return postTags.stream()
                       .map(postTag -> postTag.getTag().getName()) // 각 PostTag에서 Tag의 name 필드 추출
                       .collect(Collectors.joining(",")); // ','로 연결된 문자열 생성
    }
}