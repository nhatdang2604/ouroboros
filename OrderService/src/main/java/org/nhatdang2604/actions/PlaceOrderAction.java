package org.nhatdang2604.actions;

import org.nhatdang2604.configs.KafkaTopicConfig;
import org.nhatdang2604.dtos.RawOrderDTO;
import org.nhatdang2604.entities.Order;
import org.nhatdang2604.events.KafkaProducable;
import org.nhatdang2604.events.PlaceOrderEvent;
import org.springframework.stereotype.Service;

@Service
public class PlaceOrderAction {

    private final GetTransIdAction transIdAction;
    private final KafkaTopicConfig kafkaTopicConfig;
    private final CreateOrderAction createOrderAction;
    private final ProduceKafkaMessageAction produceKafkaMessageAction;

    public PlaceOrderAction(
            GetTransIdAction transIdAction,
            KafkaTopicConfig kafkaTopicConfig,
            CreateOrderAction createOrderAction,
            ProduceKafkaMessageAction produceKafkaMessageAction
    ) {
        this.transIdAction = transIdAction;
        this.kafkaTopicConfig = kafkaTopicConfig;
        this.createOrderAction = createOrderAction;
        this.produceKafkaMessageAction = produceKafkaMessageAction;
    }

    public int exec(RawOrderDTO orderDTO) {

        String transId = transIdAction.exec();

        //Save order data to DB
        Order order = Order.parse(orderDTO);
        order.setId(transId);
        order.setStatus(Order.Status.PENDING);
        createOrderAction.exec(order);

        //Produce place event msg
        String topic = kafkaTopicConfig.getPlaceOrderTopicName();
        KafkaProducable event = new PlaceOrderEvent(transId, orderDTO);
        produceKafkaMessageAction.exec(topic, event);

        return 0;
    }
}
