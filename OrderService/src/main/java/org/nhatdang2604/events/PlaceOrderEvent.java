package org.nhatdang2604.events;

import org.nhatdang2604.dtos.RawOrderDTO;

public record PlaceOrderEvent(String transId, RawOrderDTO order) implements KafkaProducable {
    //do nothing
}
