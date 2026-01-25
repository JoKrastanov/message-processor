package com.example.message_processor.rules.action;

import com.example.message_processor.rules.dsl.DslEnum;
import com.example.message_processor.rules.dsl.DslParser;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType implements DslEnum
{
    UPDATE("update"),
    CREATE("create"),
    REMOVE("remove");
    
    private final String action;

    ActionType(String action) 
    {
        this.action = action;
    }

    @Override
    @JsonValue
    public String getValue() 
    {
        return this.action;
    }

    @JsonGetter
    public static ActionType fromValue(String action)
    {
        return DslParser.fromValue(
            ActionType.class,
            action,
            "action"
        );
    }
}
