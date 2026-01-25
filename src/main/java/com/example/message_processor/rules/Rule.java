package com.example.message_processor.rules;

import com.example.message_processor.rules.Action.Action;
import com.example.message_processor.rules.Condition.Condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rule 
{
    private String id;
    private String name;
    private String description;
    private Condition condition;
    private Action action;
}
