package com.example.message_processor.rules.dsl;

import com.example.message_processor.exception.IllegalDslValueException;

public class DslParser
{
    public static <E extends Enum<E> & DslEnum> E fromValue(Class<E> enumClass, String value, String type) 
    {
        for (E e : enumClass.getEnumConstants()) 
        {
            if (e.getValue().equals(value)) 
            {
                return e;
            }
        }
        throw new IllegalDslValueException(type, value);
    }
}