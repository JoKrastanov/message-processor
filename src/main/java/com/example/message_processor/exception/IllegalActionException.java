package com.example.message_processor.exception;

public class IllegalActionException extends RuntimeException 
{

    public IllegalActionException(String action) 
    {
        super("Illegal action: " + action);
    }

    public IllegalActionException(String action, Throwable cause) 
    {
        super("Illegal action: " + action, cause);
    }
}

