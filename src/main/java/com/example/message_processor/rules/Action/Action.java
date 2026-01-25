package com.example.message_processor.rules.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Action 
{
    private String field;
    private ActionType actionType;
    private String expression;
}
