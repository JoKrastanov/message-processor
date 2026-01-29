package com.example.message_processor.messaging.utils;

import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Utils {

    public static ObjectNode findDelta(
        JsonNode a,
        JsonNode b
    )
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode delta = mapper.createObjectNode();

        Iterator<String> fieldNames = b.fieldNames();
        while(fieldNames.hasNext())
        {
            String field = fieldNames.next();
            JsonNode aFieldValue = a.get(field);
            JsonNode bFieldValue = b.get(field);

            if(aFieldValue == null)
            {
                // Exists in b but not in a => add to delta
                delta.set(field, bFieldValue);
            }
            else if(!aFieldValue.equals(bFieldValue))
            {
                // Field exists but has been modified
                if(aFieldValue.isObject() && bFieldValue.isObject())
                {
                    ObjectNode objectDelta = findDelta(
                        aFieldValue,
                        bFieldValue
                    );

                    if(objectDelta.size() > 0)
                    {
                        // There are changes in the nested object
                        delta.set(field, objectDelta);
                    }
                }
                else
                {
                    // Change in field value that is not an object
                    delta.set(field, bFieldValue);
                }
            }
        }

        return delta;
    }

    public static Map<String, Object> deepCopy(
        Map<String, Object> map
    )
    {
        try
        {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
            mapper.writeValueAsString(map),
            new TypeReference<Map<String, Object>>() {}
        );
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException("Malformed Json");
        }
    }

}
