package com.example.message_processor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.message_processor.rules.action.Action;
import com.example.message_processor.rules.action.ActionExecutionService;
import com.example.message_processor.rules.action.ActionType;
import com.example.message_processor.rules.utils.FieldPathUtils;
import com.example.message_processor.utils.MockData;

public class ActionExecutionServiceTests {
    
    private ActionExecutionService actionExecutionService;

    @BeforeEach
    void setUp()
    {
        this.actionExecutionService = new ActionExecutionService();
    }

    void apply(
        Action action,
        Map<String, Object> message
    )
    {
        this.actionExecutionService.apply(action, message);
    }

    @Test
    void testAction_applyCreate()
    {
        Map<String, Object> message = MockData.generate();
        Action testAction = new Action(
            "user.job.isHired",
            ActionType.CREATE,
            "of course"
        );

        this.apply(
            testAction,
            message
        );

        Map<String, Object> userMap = FieldPathUtils.castToMap(
            message.get("user")
        );

        Map<String, Object> userJobStatus = FieldPathUtils.castToMap(
            userMap.get("job")
        );

        assertEquals(
            "of course",
            userJobStatus.get("isHired")
        );
    }
    
    @Test
    void testAction_Update()
    {
        Map<String, Object> message = MockData.generate();
        Action testAction = new Action(
            "user.name",
            ActionType.UPDATE,
            "Krastanov"
        );

        this.apply(
            testAction,
            message
        );

        Map<String, Object> userMap = FieldPathUtils.castToMap(
            message.get("user")
        );

        assertEquals(
            "Krastanov",
            userMap.get("name")
        );
    }

    @Test
    void testAction_applyUpdateNotExistingPath()
    {
        Map<String, Object> message = MockData.generate();
        Action testAction = new Action(
            "user.job.isHired",
            ActionType.UPDATE,
            "of course"
        );

        assertThrows(
            RuntimeException.class,
            () ->
                this.apply(
                    testAction,
                    message
                ) 
            );
    }
   
    @Test
    void testAction_Delete()
    {
        Map<String, Object> message = MockData.generate();
        Action testAction = new Action(
            "user.name",
            ActionType.REMOVE,
            "Krastanov"
        );

        this.apply(
            testAction,
            message
        );

        Map<String, Object> userMap = FieldPathUtils.castToMap(
            message.get("user")
        );

        assertEquals(
            null,
            userMap.get("name")
        );
    }

    @Test
    void testAction_applyCreateWithComputedField()
    {
        Map<String, Object> message = MockData.generate();
        Action testAction = new Action(
            "user.fullName",
            ActionType.CREATE,
            "${user.name} ${user.lastName}"
        );

        this.apply(
            testAction,
            message
        );

        Map<String, Object> userMap = FieldPathUtils.castToMap(
            message.get("user")
        );

        assertEquals(
            "Joan Krastanov",
            userMap.get("fullName")
        );
    }
}
