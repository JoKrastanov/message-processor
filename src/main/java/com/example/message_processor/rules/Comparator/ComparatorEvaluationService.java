package com.example.message_processor.rules.comparator;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.message_processor.rules.utils.FieldPathUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComparatorEvaluationService {
    public boolean evaluate(
        Comparator comparator,
        Map<String, Object> message
    )
    {
        Object fieldValue = 
            FieldPathUtils.getFieldValueFromPath(
                comparator.getField(),
                message
            );
        
        Object comparatorValue =
            comparator.getValue();

        return switch (comparator.getComparatorType())
        {
            case EQUALS -> this.evaluateEquals(
                fieldValue,
                comparatorValue
            );
            case NOT_EQUALS -> this.evaluateNotEquals(
                fieldValue,
                comparatorValue
            );
            case GREATER_THAN -> this.evaluateGreaterThan(
                fieldValue,
                comparatorValue
            );
            case LESS_THAN -> this.evaluateLessThan(
                fieldValue,
                comparatorValue
            );
            case GREATER_OR_EQUAL -> this.evaluateGreaterThanOrEquals(
                fieldValue,
                comparatorValue
            );
            case LESS_OR_EQUAL -> this.evaluateLessThanOrEquals(
                fieldValue,
                comparatorValue
            );
            case CONTAINS -> this.evaluateContains(
                fieldValue,
                comparatorValue
            );
            case DEFINED -> this.evaluateDefined(
                fieldValue
            );
            case NOT_DEFINED -> this.evaluateNotDefined(
                fieldValue
            );
            default ->
            {
                //! Comparator type does not exist
                yield false;
            }
        };
    }

    private boolean evaluateEquals(
        Object fieldValue,
        Object comparatorValue
    )
    {
        return Objects.equals(fieldValue, comparatorValue);
    }

    private boolean evaluateNotEquals(
        Object fieldValue,
        Object comparatorValue
    )
    {
       return !this.evaluateEquals(fieldValue, comparatorValue);
    }

    private boolean evaluateGreaterThan(
        Object fieldValue,
        Object comparatorValue
    )
    {
        if(
            !(fieldValue instanceof Number) ||
            !(comparatorValue instanceof Number)
        )
        {
            // ! Incorrect type check
            throw new RuntimeException();
        }

       return ((Number) fieldValue).doubleValue() > ((Number)comparatorValue).doubleValue();
    }

    private boolean evaluateLessThan(
        Object fieldValue,
        Object comparatorValue
    )
    {
        if(
            !(fieldValue instanceof Number) ||
            !(comparatorValue instanceof Number)
        )
        {
            // ! Incorrect type check
            throw new RuntimeException();
        }

       return ((Number) fieldValue).doubleValue() < ((Number)comparatorValue).doubleValue();
    }

    private boolean evaluateGreaterThanOrEquals(
        Object fieldValue,
        Object comparatorValue
    )
    {
        return this.evaluateEquals(fieldValue, comparatorValue) ||
            this.evaluateGreaterThan(fieldValue, comparatorValue);
    }

    private boolean evaluateLessThanOrEquals(
        Object fieldValue,
        Object comparatorValue
    )
    {
        return this.evaluateEquals(fieldValue, comparatorValue) ||
            this.evaluateLessThan(fieldValue, comparatorValue);
    }

    private boolean evaluateContains(
        Object fieldValue,
        Object comparatorValue
    ) 
    {
        if (
            fieldValue instanceof String s &&
            comparatorValue instanceof String sub
        ) 
        {
            return s.contains(sub);
        }

        if (fieldValue instanceof Iterable<?> collection) 
        {
            for (Object item : collection) 
            {
                if (
                    item != null &&
                    item.equals(comparatorValue)
                )
                {
                    return true;
                } 
            }
            return false;
        }

        if (fieldValue.getClass().isArray()) 
        {
            int length = Array.getLength(fieldValue);
            for (int i = 0; i < length; i++)
            {
                Object item = Array.get(fieldValue, i);
                if (
                    fieldValue != null &&
                    comparatorValue.equals(item)
                )
                {
                    return true;
                }
            }
            return false;
        }

        return false;
    }

    private boolean evaluateDefined(
        Object fieldValue
    )
    {
        return !this.evaluateEquals(
            fieldValue,
            null
        );
    }

    private boolean evaluateNotDefined(
        Object fieldValue
    )
    {
        return !this.evaluateDefined(fieldValue);
    }


}
