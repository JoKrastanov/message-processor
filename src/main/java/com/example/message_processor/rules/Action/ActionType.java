package com.example.message_processor.rules.Action;

import com.example.message_processor.exception.IllegalActionException;

public enum ActionType 
{
    UPDATE("update"),
    CREATE("create"),
    REMOVE("remove");
    
    private final String action;

    ActionType(String action) 
    {
        this.action = action;
    }

    public String getAction() 
    {
        return this.action;
    }

    public static ActionType fromSymbol(String action) 
    {
        for (ActionType type : values()) 
        {
            if (type.action.equals(action)) 
            {
                return type;
            }
        }
        throw new IllegalActionException(action);
    }
}
