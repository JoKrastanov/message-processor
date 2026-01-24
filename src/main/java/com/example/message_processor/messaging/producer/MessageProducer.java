package com.example.message_processor.messaging.producer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageProducer 
{
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.producer.group_id}")
    private String producerGroupId;

    public void sendMessage(Map<String, Object> payload)
    {
        try
        {
            kafkaTemplate.send(
                this.producerGroupId,
                "test output"
            ).whenComplete(
                (result, ex) ->
                {
                    if (ex != null)
                    {
                        log.error("ERROR: {}", ex);
                    }
                    else
                    {
                        log.info("SUCCESS");
                    } 
                }
            );
        }
        catch (Exception e)
        {
            log.error("Error sending message");
            throw new RuntimeException(e);
        }
    }
}
