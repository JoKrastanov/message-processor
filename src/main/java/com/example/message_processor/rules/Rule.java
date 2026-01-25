package com.example.message_processor.rules;

import java.util.List;

import com.example.message_processor.rules.action.Action;
import com.example.message_processor.rules.condition.Condition;

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
    private List<Action> actions;
}
