package com.example.message_processor.rules.comparator;

import com.example.message_processor.rules.dsl.DslEnum;
import com.example.message_processor.rules.dsl.DslParser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ComparatorType implements DslEnum
{
    EQUALS("=="),
    NOT_EQUALS("!="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_OR_EQUAL(">="),
    LESS_OR_EQUAL("<="),
    CONTAINS("contains"),
    DEFINED("defined"),
    NOT_DEFINED("not_defined");

    private final String comparator;

    ComparatorType(String comparator) 
    {
        this.comparator = comparator;
    }

    @Override
    @JsonValue
    public String getValue() 
    {
        return this.comparator;
    }

    @JsonCreator
    public static ComparatorType fromValue(String comparator) 
    {
        return DslParser.fromValue(
            ComparatorType.class,
            comparator,
            "comparator"
        );
    }
}
   
