package com.example.message_processor.rules.action;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.message_processor.rules.utils.FieldPathUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActionExecutionService {
    public void apply(
        Action action,
        Map<String, Object> message
    )
    {
        String field = action.getField();
        String expression = action.getExpression();
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
                // ! Action type does not exist
                throw new RuntimeException();
            }
        }
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
