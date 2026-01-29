package com.example.message_processor.rules.condition;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.message_processor.exception.IllegalDslValueException;
import com.example.message_processor.rules.comparator.Comparator;
import com.example.message_processor.rules.comparator.ComparatorEvaluationService;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ConditionEvaluationService {

    @Nonnull
    private final ComparatorEvaluationService comparatorEvaluationService;

    public boolean evaluate(Condition condition, Map<String, Object> message)
    {
        List<Comparator> comparators = condition.getComparators();
        List<Condition> subconditions = condition.getSubconditions();

        return switch (condition.getType())
        {
            case ALL -> this.evaluateAll(
                comparators,
                subconditions,
                message
            );
            case ANY -> this.evaluateAny(
                comparators,
                subconditions,
                message
            );
            case NONE -> this.evaluateNone(
                comparators,
                subconditions,
                message
            );
            default ->
            {
                throw new IllegalDslValueException(
                    "condition",
                    condition.getType().getValue()
                );
            }
        };
    }

    private boolean evaluateAll(
        List<Comparator> comparators,
        List<Condition> subconditions,
        Map<String, Object> message
    )
    {
        boolean comparatorsPassCondition = 
            comparators.isEmpty() ||
            comparators
                .stream()
                .allMatch(
                    comparator ->
                        this.comparatorEvaluationService.evaluate(
                            comparator,
                            message
                        )
                );

        boolean subconditionsPass =
            subconditions.isEmpty() ||
            subconditions
                .stream()
                .allMatch(
                    subcondition ->
                        this.evaluate(
                            subcondition,
                            message
                        )
                );

        return comparatorsPassCondition && subconditionsPass;
    }

    private boolean evaluateAny( 
        List<Comparator> comparators,
        List<Condition> subconditions,
        Map<String, Object> message
    )
    {
        boolean comparatorsPassCondition = 
            comparators.isEmpty() ||
            comparators
                .stream()
                .anyMatch(
                    comparator ->
                        this.comparatorEvaluationService.evaluate(
                            comparator,
                            message
                        )
                );

        boolean subconditionsPass =
            subconditions.isEmpty() ||
            subconditions
                .stream()
                .anyMatch(
                    subcondition ->
                        this.evaluate(
                            subcondition,
                            message
                        )
                );

        return comparatorsPassCondition && subconditionsPass;
    }

    private boolean evaluateNone( 
        List<Comparator> comparators,
        List<Condition> subconditions,
        Map<String, Object> message
    )
    {
        boolean comparatorsPassCondition = 
            comparators.isEmpty() ||
            comparators
                .stream()
                .noneMatch(
                    comparator ->
                        this.comparatorEvaluationService.evaluate(
                            comparator,
                            message
                        )
                );

        boolean subconditionsPass =
            subconditions.isEmpty() ||
            subconditions
                .stream()
                .noneMatch(
                    subcondition ->
                        this.evaluate(
                            subcondition,
                            message
                        )
                );

        return comparatorsPassCondition && subconditionsPass;
    }
}
