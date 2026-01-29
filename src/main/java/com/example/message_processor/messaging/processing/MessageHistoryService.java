package com.example.message_processor.messaging.processing;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.message_processor.messaging.repository.MessageHistory;
import com.example.message_processor.messaging.repository.MessageHistoryRepository;
import com.example.message_processor.messaging.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHistoryService
{
    @Nonnull
    private ObjectMapper objectMapper;

    @Nonnull
    private MessageHistoryRepository messageHistoryRepository;

    public void computeMessageDelta(
        String messageUuid,
        Map<String, Object> currentMessage,
        Map<String, Object> messageAfterAction
    )
    {
        JsonNode jsonDelta = this.calculateDelta(
            currentMessage,
            messageAfterAction
        );

        MessageHistory lastMessageHistory = 
            this.messageHistoryRepository
                .findTopByMessageUuidOrderByVersionDesc(
                    messageUuid
                );

        int lastMessageVersion = 
            lastMessageHistory == null ?
                1 :
                lastMessageHistory.getVersion() + 1;

        this.messageHistoryRepository.save(
            MessageHistory.builder()
                .messageUuid(messageUuid)
                .version(lastMessageVersion)
                .originalMessage(currentMessage)
                .processedMessage(messageAfterAction)
                .messageDelta(jsonDelta)
                .time_processed(LocalDateTime.now())
                .build()
        );
    }


    public JsonNode calculateDelta(
        Map<String, Object> currentMessage,
        Map<String, Object> messageAfterAction
    )
    {
        JsonNode currentJson = this.objectMapper.convertValue(
            currentMessage,
            JsonNode.class
        );

        JsonNode modifiedJson = this.objectMapper.convertValue(
            messageAfterAction,
            JsonNode.class
        );

        return Utils.findDelta(currentJson, modifiedJson);
    }
}
