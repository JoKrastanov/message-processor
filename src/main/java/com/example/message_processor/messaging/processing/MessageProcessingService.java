package com.example.message_processor.messaging.processing;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.message_processor.messaging.producer.MessageProducer;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProcessingService {
    @Nonnull
    private final MessageProducer messageProducer;
    
    public void processMessage(
        String message
    )
    {
        Map<String, Object> outputMessage = new HashMap<>();
        outputMessage.put("key", message);
        messageProducer.sendMessage(outputMessage);
    }
}
