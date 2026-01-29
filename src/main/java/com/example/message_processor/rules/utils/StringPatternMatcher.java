package com.example.message_processor.rules.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPatternMatcher {

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([^}]+)\\}");

    public static Matcher getVariables(String template) {
        return VARIABLE_PATTERN.matcher(template);
    }
}
