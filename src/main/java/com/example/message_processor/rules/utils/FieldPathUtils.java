package com.example.message_processor.rules.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class FieldPathUtils {

    private FieldPathUtils() {}

    public static Object getFieldValueFromPath(
        String field,
        Map<String, Object> message
    )
    {
        String[] fieldPaths = getFieldPath(field);

        Object value = message;

        for (String key : fieldPaths)
        {
            if(isNotMap(value))
            {
                // ! Incorrect path exception
                return null;
            }
            value = castToMap(value).get(key);
        }
        return value;
    }

    public static void setOrCreateFieldValueAtPath(
        String field,
        Map<String, Object> message,
        Object expression,
        boolean forceCreatePath
    )
    {
        Object value = message;
        String[] fieldPaths = getFieldPath(field);
        for (int i = 0; i < fieldPaths.length; i++)
        {
            String key = fieldPaths[i];
            boolean isLast = i == fieldPaths.length - 1;

            Map<String, Object> map = castToMap(value);

            if (isLast)
            {
                map.put(key, expression);
            }
            else
            {
                Object nextValue = map.get(key);
                // Current key does not exist but we force creation
                if (isNotMap(nextValue) && forceCreatePath)
                {
                    nextValue = new HashMap<String, Object>();
                    map.put(key, nextValue);
                }
                else if(isNotMap(nextValue)) 
                {
                    // ! Incorrect path exception
                    throw new RuntimeException(
                        String.format(
                            "KYS %s, %s, %s, %b, %b",
                            nextValue.getClass(),
                            key,
                            nextValue,
                            isNotMap(nextValue),
                            forceCreatePath
                        )
                    );
                }
                value = nextValue;
            }
        }
    }

    public static void removeFieldValueAtPath(
        String field,
        Map<String, Object> message
    )
    {
        Object value = message;
        String[] fieldPaths = getFieldPath(field);
        for (int i = 0; i < fieldPaths.length; i++)
        {
            String key = fieldPaths[i];
            boolean isLast = i == fieldPaths.length - 1;

            Map<String, Object> map = castToMap(value);

            if (isLast)
            {
                map.remove(key);
            }
            else 
            {
                value = map.get(key);
            }
        }
    }

    private static String[] getFieldPath(String field)
    {
        return field.split(
            Pattern.quote(".")
        );
    }

    private static boolean isNotMap(Object obj)
    {
        return !(obj instanceof Map);
    }

    // Expected error from the way we cast the value variable
    @SuppressWarnings("unchecked")
    public static Map<String, Object> castToMap(Object obj) 
    {
        if (obj instanceof Map<?, ?> map)
        {
            return (Map<String, Object>) map;
        }
        throw new RuntimeException();
    }
}
