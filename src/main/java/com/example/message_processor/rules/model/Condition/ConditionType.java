package com.example.message_processor.rules.model.Condition;

import com.example.message_processor.exception.IllegalConditionException;

public enum ConditionType 
{
    ALL("all"),
    ANY("any"),
    NONE("any"),
    NOT("not");

    private final String condition;

    ConditionType(String condition) 
    {
        this.condition = condition;
    }

    public String getCondition() 
    {
        return this.condition;
    }

    public static ConditionType fromSymbol(String condition) 
    {
        for (ConditionType type : values()) 
        {
            if (type.condition.equals(condition)) 
            {
                return type;
            }
        }
        throw new IllegalConditionException(condition);
    }
}
