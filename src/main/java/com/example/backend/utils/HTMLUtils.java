package com.example.backend.utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

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

    public static String markdownToHtml(String markdown) {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(markdown);
        return renderer.render(document);  // HTML로 변환된 문자열을 반환
    }
}