package com.example.message_processor.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.message_processor.messaging.processing.MessageProcessingService;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer 
{

    @Nonnull
    private final MessageProcessingService messageProcessingService;

    @KafkaListener(topics = "${spring.kafka.consumer.group_id}")
    public void consume(String message)
    {
        log.info("Received message from Kafka: {}", message);
        this.messageProcessingService.processMessage(message);
    }
}
