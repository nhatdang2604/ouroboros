package org.nhatdang2604.actions;

import org.nhatdang2604.events.KafkaSendable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduceKafkaMessageAction {

    private final KafkaTemplate<String, KafkaSendable> kafkaTemplate;

    public ProduceKafkaMessageAction(KafkaTemplate<String, KafkaSendable> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public int exec(String topic, KafkaSendable message) {
        kafkaTemplate.send(topic, message);
        return 0;
    }

}
