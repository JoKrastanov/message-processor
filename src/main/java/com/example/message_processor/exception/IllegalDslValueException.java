package com.example.message_processor.exception;

public class IllegalDslValueException extends RuntimeException 
{

    public IllegalDslValueException(String type, String value) 
    {
        super(
            String.format(
                "Illegal {} wtih value: {}",
                type,
                value
            )
        );
    }
}

