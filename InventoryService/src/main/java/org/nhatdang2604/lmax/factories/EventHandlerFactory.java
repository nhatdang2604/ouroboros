package org.nhatdang2604.lmax.factories;

import com.lmax.disruptor.EventHandler;
import org.nhatdang2604.constants.EventConstant;
import org.nhatdang2604.lmax.handlers.PlaceOrderHandler;
import org.nhatdang2604.lmax.slots.RingBufferSlot;

public class EventHandlerFactory {

    public EventHandler<RingBufferSlot> make(String type) {
        switch (type) {
            case EventConstant.TYPE_PLACE_ORDER: {
                return new PlaceOrderHandler();
            }
            default: {
                return null;
            }
        }
    }
}
