package com.example.message_processor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.message_processor.rules.comparator.Comparator;
import com.example.message_processor.rules.comparator.ComparatorEvaluationService;
import com.example.message_processor.rules.comparator.ComparatorType;
import com.example.message_processor.utils.MockData;

public class ComparatorEvaluationServiceTests 
{

    private ComparatorEvaluationService comparatorEvaluationService;

    @BeforeEach
    void setUp()
    {
        this.comparatorEvaluationService = new ComparatorEvaluationService();
    }

    boolean evaluate(
        Comparator comparator,
        Map<String, Object> message
    )
    {
        return this.comparatorEvaluationService.evaluate(
            comparator,
            message
        );
    }

    @Test
    void testComparator_evaluateEquals()
    {
        Map<String, Object> message = MockData.generate();
        Comparator testComparator = new Comparator(
            "user.age",
            ComparatorType.EQUALS,
            23    
        );

        assertEquals(
            true,
            this.evaluate(
                testComparator,
                message
            )
        );
    }

    @Test
    void testComparator_evaluateNotEquals()
    {
        Map<String, Object> message = MockData.generate();
        Comparator testComparator = new Comparator(
            "user.age",
            ComparatorType.NOT_EQUALS,
            100    
        );

        assertEquals(
            true,
            this.evaluate(
                testComparator,
                message
            )
        );
    }

    @Test
    void testComparator_evaluateGreaterThan()
    {
        Map<String, Object> message = MockData.generate();
        Comparator testComparator = new Comparator(
            "user.age",
            ComparatorType.GREATER_THAN,
            18    
        );

        assertEquals(
            true,
            this.evaluate(
                testComparator,
                message
            )
        );
    }

    @Test
    void testComparator_evaluateGreaterThanIncorrectInput()
    {
        Map<String, Object> message = MockData.generate();
        Comparator testComparator = new Comparator(
            "user.age",
            ComparatorType.GREATER_THAN,
            "hello world"
        );

        assertThrows(
            IllegalArgumentException.class,
            () ->
                this.evaluate(testComparator, message)
        );
    }

    @Test
    void testComparator_evaluateLessThan()
    {
        Map<String, Object> message = MockData.generate();
        Comparator testComparator = new Comparator(
            "user.age",
            ComparatorType.LESS_THAN,
            100 
        );

        assertEquals(
            true,
            this.evaluate(
                testComparator,
                message
            )
        );
    }

    @Test
    void testComparator_evaluateLessThanIncorrectInput()
    {
        Map<String, Object> message = MockData.generate();
        Comparator testComparator = new Comparator(
            "user.age",
            ComparatorType.LESS_THAN,
            "hello world"
        );

        assertThrows(
            IllegalArgumentException.class,
            () ->
                this.evaluate(testComparator, message)
        );
    }

    @Test
    void testComparator_evaluateContainsString()
    {
        Map<String, Object> message = MockData.generate();
        Comparator testComparator = new Comparator(
            "user.name",
            ComparatorType.CONTAINS,
            "Jo"
        );

        assertEquals(
            true,
            this.evaluate(
                testComparator,
                message
            )
        );
    }

    @Test
    void testComparator_evaluateContainsArray()
    {
        Map<String, Object> message = MockData.generate();
        Comparator testComparator = new Comparator(
            "user.languages",
            ComparatorType.CONTAINS,
            "Bulgarian"
        );

        assertEquals(
            true,
            this.evaluate(
                testComparator,
                message
            )
        );
    }
    
    @Test
    void testComparator_evaluateContainsDefined()
    {
        Map<String, Object> message = MockData.generate();
        Comparator testComparator = new Comparator(
            "user.nationality",
            ComparatorType.DEFINED,
            ""
        );

        assertEquals(
            true,
            this.evaluate(
                testComparator,
                message
            )
        );
    }

    @Test
    void testComparator_evaluateContainsNotDefined()
    {
        Map<String, Object> message = MockData.generate();
        Comparator testComparator = new Comparator(
            "user.isBillionare",
            ComparatorType.NOT_DEFINED,
            ""
        );

        assertEquals(
            true,
            this.evaluate(
                testComparator,
                message
            )
        );
    }
}
