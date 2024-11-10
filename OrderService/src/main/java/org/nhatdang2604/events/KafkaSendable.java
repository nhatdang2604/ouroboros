package org.nhatdang2604.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.nhatdang2604.constants.EventConstant;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Use "name" for type identification
        property = "type" // The "type" field in JSON will determine which class to use
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlaceOrderEvent.class, name = EventConstant.TYPE_PLACE_ORDER),
})
public interface KafkaSendable {
    //do nothing
}
