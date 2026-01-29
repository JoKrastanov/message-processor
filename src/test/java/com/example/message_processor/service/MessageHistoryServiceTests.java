package com.example.message_processor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.message_processor.messaging.processing.MessageHistoryService;
import com.example.message_processor.messaging.repository.MessageHistoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageHistoryServiceTests {

    @Mock
    private MessageHistoryRepository messageHistoryRepository;

    private ObjectMapper objectMapper;

    private MessageHistoryService messageHistoryService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        this.objectMapper = new ObjectMapper();
        this.messageHistoryService = new MessageHistoryService(
            this.objectMapper,
            this.messageHistoryRepository
        );
    }

    @Test
    void testHistory_findDelta()
    {
        Map<String, Object> a = new HashMap<>();
        Map<String, Object> b = new HashMap<>();

        a.put("name", "Joan");
        b.put("name", "Joan");

        b.put("age", 23);

        Map<String, Object> nestedA = new HashMap<>();
        Map<String, Object> nestedB = new HashMap<>();

        nestedA.put("nationality", "Bulgarian");
        nestedB.put("nationality", "Bulgarian");

        nestedB.put("livesIn", "Bulgaria");

        nestedA.put("canSpeak", "[Bulgarian, English]");
        nestedB.put("canSpeak", "[Bulgarian, English, German]");

        a.put("info", nestedA);
        b.put("info", nestedB);
        
        JsonNode delta = this.messageHistoryService.calculateDelta(
            a,
            b
        );

        Map<String, Object> expectedDelta = new HashMap<>();

        expectedDelta.put("age", 23);

        Map<String, Object> nestedExpectedDelta = new HashMap<>();

        nestedExpectedDelta.put("livesIn", "Bulgaria");
        nestedExpectedDelta.put("canSpeak", "[Bulgarian, English, German]");

        expectedDelta.put("info", nestedExpectedDelta);

        assertEquals(
            this.objectMapper.convertValue(
                expectedDelta,
                JsonNode.class
            ),
            delta
        );
    }
}
