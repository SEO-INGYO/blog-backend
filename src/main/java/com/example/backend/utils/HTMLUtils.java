package com.example.backend.utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

public final class HTMLUtils {
    private static final Parser PARSER = Parser.builder().build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder().build();

    private HTMLUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String stripHtmlTags(String content) {
        return content.replaceAll("<[^>]*>", "");
    }

    public static String escapeHtml(String content) {
        return org.apache.commons.text.StringEscapeUtils.escapeHtml4(content);
    }

    public static String convertMarkdownToHtml(String markdown) {
        return RENDERER.render(PARSER.parse(markdown));
    }
}