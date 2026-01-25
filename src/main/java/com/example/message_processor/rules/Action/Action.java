package com.example.message_processor.rules.Action;

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
    private ActionType operator;
    private String expressionString;
}
