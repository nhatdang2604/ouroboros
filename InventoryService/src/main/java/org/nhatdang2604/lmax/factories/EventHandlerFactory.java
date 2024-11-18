package org.nhatdang2604.lmax.factories;

import com.lmax.disruptor.EventHandler;
import org.nhatdang2604.constants.EventConstant;
import org.nhatdang2604.lmax.handlers.PlaceOrderHandler;
import org.nhatdang2604.lmax.slots.RingBufferSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventHandlerFactory {

    @Autowired
    private PlaceOrderHandler placeOrderHandler;

    public EventHandler<RingBufferSlot> make(String type) {
        switch (type) {
            case EventConstant.TYPE_PLACE_ORDER: {
                return placeOrderHandler;
            }
            default: {
                return null;
            }
        }
    }
}
