package com.example.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.HashMap;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 데이터를 JSON 문자열로 변환하는 유틸리티 메서드
    public static String convertToJsonString(String key, String value) throws Exception {
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put(key, value);
        return objectMapper.writeValueAsString(jsonData);
    }

    // 데이터를 JSON 문자열로 변환하는 유틸리티 메서드 (여러 키-값 쌍을 지원)
    public static String convertToJsonString(Map<String, String> keyValues) throws Exception {
        return objectMapper.writeValueAsString(keyValues);
    }
}
