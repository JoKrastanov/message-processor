package com.example.message_processor.rules.Comparator;

import com.example.message_processor.exception.IllegalOperatorException;

public enum ComparatorType 
{
    EQUALS("=="),
    NOT_EQUALS("!="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_OR_EQUAL(">="),
    LESS_OR_EQUAL("<="),
    CONTAINS("contains"),
    IN("in");

    private final String comparator;

    ComparatorType(String comparator) 
    {
        this.comparator = comparator;
    }

    public String getcomparator() 
    {
        return this.comparator;
    }

    public static ComparatorType fromcomparator(String comparator) 
    {
        for (ComparatorType type : values()) 
        {
            if (type.comparator.equals(comparator)) 
            {
                return type;
            }
        }
        throw new IllegalOperatorException(comparator);
    }
}
   
