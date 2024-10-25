package org.nhatdang2604.actions;

import org.nhatdang2604.configs.KafkaTopicConfig;
import org.nhatdang2604.dtos.RawOrderDTO;
import org.nhatdang2604.events.KafkaProducable;
import org.nhatdang2604.events.PlaceOrderEvent;
import org.springframework.stereotype.Service;

@Service
public class PlaceOrderAction {

    private final GetTransIdAction transIdAction;
    private final ProduceKafkaMessageAction produceKafkaMessageAction;
    private final KafkaTopicConfig kafkaTopicConfig;

    public PlaceOrderAction(
            GetTransIdAction transIdAction,
            ProduceKafkaMessageAction produceKafkaMessageAction,
            KafkaTopicConfig kafkaTopicConfig
    ) {
        this.transIdAction = transIdAction;
        this.produceKafkaMessageAction = produceKafkaMessageAction;
        this.kafkaTopicConfig = kafkaTopicConfig;
    }

    public int exec(RawOrderDTO order)
    {
        String topic          = kafkaTopicConfig.getPlaceOrderTopicName();
        String transId        = transIdAction.exec();
        KafkaProducable event = new PlaceOrderEvent(transId, order);
        produceKafkaMessageAction.exec(topic, event);

        return 0;
    }
}
