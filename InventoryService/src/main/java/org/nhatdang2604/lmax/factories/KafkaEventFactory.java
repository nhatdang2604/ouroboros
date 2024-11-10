package org.nhatdang2604.lmax.factories;

import com.lmax.disruptor.EventFactory;
import org.nhatdang2604.constants.EventConstant;
import org.nhatdang2604.events.KafkaSendable;
import org.nhatdang2604.events.PlaceOrderEvent;
import org.nhatdang2604.lmax.slots.EventFromKafkaRingBufferSlot;
import org.nhatdang2604.lmax.slots.RingBufferSlot;

public class KafkaEventFactory implements EventFactory<RingBufferSlot> {

    private String type;

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public RingBufferSlot newInstance() {
        switch (type) {
            case EventConstant.TYPE_PLACE_ORDER: {
                KafkaSendable event = new PlaceOrderEvent(null, null);
                return new EventFromKafkaRingBufferSlot(event);
            }
            default: {
                return null;
            }
        }
    }
}
