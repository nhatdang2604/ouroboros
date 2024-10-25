package org.nhatdang2604.actions;

import org.nhatdang2604.events.KafkaProducable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduceKafkaMessageAction {

    private final KafkaTemplate<String, KafkaProducable> kafkaTemplate;

    public ProduceKafkaMessageAction(KafkaTemplate<String, KafkaProducable> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public int exec(String topic, KafkaProducable message) {
        kafkaTemplate.send(topic, message);
        return 0;
    }

}
