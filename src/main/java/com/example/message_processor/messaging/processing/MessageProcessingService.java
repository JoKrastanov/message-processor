package com.example.message_processor.messaging.processing;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.kafka.common.Uuid;
import org.springframework.stereotype.Service;

import com.example.message_processor.messaging.producer.MessageProducer;
import com.example.message_processor.messaging.repository.Message;
import com.example.message_processor.messaging.repository.MessageRepository;
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

    @Nonnull
    private final MessageRepository messageRepository;

    @Nonnull
    private final MessageHistoryService messageDeltaService;
    
    public void processMessage(String message)
    {
        try
        {
            Map<String, Object> parsedMessage =
                this.objectMapper.readValue(
                    message,
                    new TypeReference<>() {}
                );
            
            Map<String, Object> originalMessage =
                this.objectMapper.readValue(
                    message,
                    new TypeReference<>() {}
                );

            Message processedMessage = this.generateProcessedMessage(
                originalMessage
            );

            this.ruleProcessingService.applyRules(
                parsedMessage,
                processedMessage
            );

            messageProducer.sendMessage(parsedMessage);
            messageRepository.save(processedMessage);
        }
        catch(Exception e)
        {
            log.error("Error processing message ;(", e);
            // TODO: Throw error processing message
        }
    }

    private Message generateProcessedMessage(Map<String , Object> message)
    {
        return Message.builder()
            .uuid(Uuid.randomUuid().toString())
            .originalMessage(message)
            .status("PROCESSING")
            .time_received(LocalDateTime.now())
            .build();
    }
}
