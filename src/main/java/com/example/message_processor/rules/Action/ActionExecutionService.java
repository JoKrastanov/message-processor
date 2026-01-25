package com.example.message_processor.rules.action;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActionExecutionService {
    public void apply(
        Action action,
        Map<String, Object> message,
        Map<String, Object> output
    )
    {

    }
}
