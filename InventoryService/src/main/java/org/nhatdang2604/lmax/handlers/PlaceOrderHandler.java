package org.nhatdang2604.lmax.handlers;

import org.nhatdang2604.events.PlaceOrderEvent;
import org.nhatdang2604.lmax.slots.RingBufferSlot;

public class PlaceOrderHandler extends BaseEventHandler {

    @Override
    public void onEvent(RingBufferSlot slot, long l, boolean b) throws Exception {
        PlaceOrderEvent placeOrderEvent = (PlaceOrderEvent) slot.getSlot();
        System.out.println("PlaceOrderHandler received event: " + placeOrderEvent);
    }
}
