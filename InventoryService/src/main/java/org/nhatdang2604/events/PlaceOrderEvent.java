package org.nhatdang2604.events;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.nhatdang2604.constants.EventConstant;
import org.nhatdang2604.dtos.RawOrderDTO;

@JsonTypeName(EventConstant.TYPE_PLACE_ORDER)
public record PlaceOrderEvent(String transId, RawOrderDTO order) implements KafkaSendable {

    //Do nothing
}
