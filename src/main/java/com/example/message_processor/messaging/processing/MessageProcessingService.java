package com.example.message_processor.messaging.processing;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.message_processor.messaging.producer.MessageProducer;
import com.example.message_processor.rules.RuleProcessingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProcessingService 
{
    @Nonnull
    private final ObjectMapper objectMapper;

    @Nonnull
    private final MessageProducer messageProducer;

    @Nonnull
    private final RuleProcessingService ruleProcessingService;
    
    public void processMessage(String message)
    {
        try
        {
            Map<String, Object> processedMessage =
                this.objectMapper.readValue(
                    message,
                    new TypeReference<>() {}
                );

            this.ruleProcessingService.applyRules(
                processedMessage
            );

            messageProducer.sendMessage(processedMessage);
        }
        catch(Exception e)
        {
            log.error("Error processing message ;(", e);
            // TODO: Throw error processing message
        }
    }
}
