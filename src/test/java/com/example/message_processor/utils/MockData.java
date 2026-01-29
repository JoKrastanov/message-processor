package com.example.message_processor.utils;

import java.util.HashMap;
import java.util.Map;

public class MockData {
    public static Map<String, Object> generate()
    {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> userObject = new HashMap<>();
        message.put("user", userObject);

        userObject.put("name", "Joan");
        userObject.put("age", 23);
        userObject.put("nationality", "Bulgarian");

        String[] languages = 
        {
            "Bulgarian",
            "English"
        };

        userObject.put("languages", languages);

        return message;
    }
}
