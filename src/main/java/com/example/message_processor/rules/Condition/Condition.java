package com.example.message_processor.rules.Condition;

import java.util.List;

import com.example.message_processor.rules.Comparator.Comparator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Condition 
{
       private ConditionType type;
       private List<Comparator> comparators;
       private List<Condition> subconditions;
}
