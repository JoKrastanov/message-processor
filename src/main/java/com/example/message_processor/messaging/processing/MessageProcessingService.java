package com.example.message_processor.messaging.processing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.message_processor.messaging.producer.MessageProducer;
import com.example.message_processor.rules.Rule;
import com.example.message_processor.rules.RuleLoaderService;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProcessingService 
{
    @Nonnull
    private final MessageProducer messageProducer;

    @Nonnull
    private final RuleLoaderService ruleLoaderService;
    
    public void processMessage(
        String message
    )
    {
        Map<String, Object> outputMessage = new HashMap<>();
        List<Rule> rule = ruleLoaderService.getRules();
        outputMessage.put("key", message);
        messageProducer.sendMessage(outputMessage);
    }
}
