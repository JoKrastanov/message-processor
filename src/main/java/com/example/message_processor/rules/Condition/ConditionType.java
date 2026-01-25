package com.example.message_processor.rules.condition;

import com.example.message_processor.rules.dsl.DslEnum;
import com.example.message_processor.rules.dsl.DslParser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ConditionType implements DslEnum
{
    ALL("all"),
    ANY("any"),
    NONE("none");

    private final String condition;

    ConditionType(String condition) 
    {
        this.condition = condition;
    }

    @Override
    @JsonValue
    public String getValue() 
    {
        return this.condition;
    }

    @JsonCreator
    public static ConditionType fromValue(String condition)
    {
        return DslParser.fromValue(
            ConditionType.class,
            condition,
            "condition"
        );
    }
}
