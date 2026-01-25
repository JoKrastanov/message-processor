package com.example.message_processor.rules.condition;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.message_processor.rules.comparator.Comparator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ConditionEvaluationService {

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
                yield false;
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
                        this.evaluateComparator(
                            comparator
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
                        this.evaluateComparator(
                            comparator
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
                        this.evaluateComparator(
                            comparator
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

    private boolean evaluateComparator(Comparator comparator)
    {
        return false;
    }
}
