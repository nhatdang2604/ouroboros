package org.nhatdang2604.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.nhatdang2604.events.KafkaSendable;

public class JsonSerializer implements Serializer<KafkaSendable> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, KafkaSendable toSerialize) {
        try {
            return objectMapper.writeValueAsBytes(toSerialize);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing value: " + e.getMessage(), e);
        }
    }
}