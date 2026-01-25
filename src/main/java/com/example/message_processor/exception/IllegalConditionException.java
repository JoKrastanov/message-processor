package com.example.message_processor.exception;

public class IllegalConditionException extends RuntimeException 
{

    public IllegalConditionException(String condition) 
    {
        super("Illegal condition: " + condition);
    }

    public IllegalConditionException(String condition, Throwable cause) 
    {
        super("Illegal condition: " + condition, cause);
    }
}

