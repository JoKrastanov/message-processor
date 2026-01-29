package com.example.message_processor.rules.action;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;

import org.springframework.stereotype.Service;

import com.example.message_processor.exception.IllegalDslValueException;
import com.example.message_processor.rules.utils.FieldPathUtils;
import com.example.message_processor.rules.utils.StringPatternMatcher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActionExecutionService {
    public void apply(
        Action action,
        Map<String, Object> message
    )
    {
        log.info(
            String.format(
                "Executing action %s %s for message: %s",
                action.getActionType().getValue(),
                action.getField(),
                message.toString()
            )
        );
        String field = action.getField();
        String expression = this.applyExpressionIfNecessary(
            action.getExpression(),
            message
        );
        switch (action.getActionType())
        {
            case CREATE:
                this.applyCreate(
                    field,
                    expression,
                    message
                );
                break;
            case UPDATE:
                this.applyUpdate(
                    field,
                    expression,
                    message
                );
                break;
            case REMOVE:
                this.applyRemove(
                    field,
                    message
                );
                break;
            default:
            {
                throw new IllegalDslValueException(
                    "action",
                    action.getActionType().getValue()
                );
            }
        }
    }

    private String applyExpressionIfNecessary(
        String expression,
        Map<String, Object> message
    )
    {
        Matcher matcher = StringPatternMatcher.getVariables(expression);
        StringBuilder output = new StringBuilder();
        
        while (matcher.find())
        {
            String fieldPath = matcher.group(1);
            Object fieldValue = 
                Optional.ofNullable(
                    FieldPathUtils.getFieldValueFromPath(
                        fieldPath,
                        message
                    )
                ).orElse("");

            matcher.appendReplacement(
                output,
                fieldValue.toString()
            );
        }

        matcher.appendTail(output);
        return output.toString();
    }

    private void applyCreate(
        String field,
        String expression,
        Map<String, Object> message
    )
    {
        FieldPathUtils.setOrCreateFieldValueAtPath(
            field,
            message,
            expression,
            true
        );
    }

    private void applyUpdate(
        String field,
        String expression,
        Map<String, Object> message
    )
    {
        FieldPathUtils.setOrCreateFieldValueAtPath(
            field,
            message,
            expression,
            false
        );
    }

    private void applyRemove(
        String field,
        Map<String, Object> message
    )
    {
        FieldPathUtils.removeFieldValueAtPath(
            field,
            message
        );
    }
}
