package com.example.message_processor.exception;

public class IllegalOperatorException extends RuntimeException 
{

    public IllegalOperatorException(String operator) 
    {
        super("Illegal operator: " + operator);
    }

    public IllegalOperatorException(String operator, Throwable cause) 
    {
        super("Illegal operator: " + operator, cause);
    }
}

