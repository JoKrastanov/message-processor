package com.example.message_processor.rules.model.Comparator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comparator 
{
    private String field;
    private ComparatorType comparatorType;
    private Object value;
}
