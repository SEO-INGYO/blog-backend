package com.example.backend.utils;

public final class HTMLUtils {
    private HTMLUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String stripHtmlTags(String content) {
        return content.replaceAll("<[^>]*>", "");
    }

    public static String escapeHtml(String content) {
        return org.apache.commons.text.StringEscapeUtils.escapeHtml4(content);
    }
}
