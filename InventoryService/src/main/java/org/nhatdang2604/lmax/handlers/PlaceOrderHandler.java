package org.nhatdang2604.lmax.handlers;

import org.nhatdang2604.actions.BatchReserveItemAction;
import org.nhatdang2604.events.PlaceOrderEvent;
import org.nhatdang2604.lmax.slots.RingBufferSlot;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class PlaceOrderHandler extends BaseEventHandler {

    private final int MAX_BATCH_SIZE = 1_000;
    private final List<PlaceOrderEvent> batchEvents = new ArrayList<>();

    private BatchReserveItemAction batchReserveItemAction;

    public PlaceOrderHandler(BatchReserveItemAction batchReserveItemAction) {
        this.batchReserveItemAction = batchReserveItemAction;
    }

    @Override
    public void onEvent(RingBufferSlot slot, long sequence, boolean endOfBatch) throws Exception {
        PlaceOrderEvent placeOrderEvent = (PlaceOrderEvent) slot.getSlot();
        batchEvents.add(placeOrderEvent);

        if (batchEvents.size() >= MAX_BATCH_SIZE || endOfBatch) {
            batchReserveItemAction.execute(batchEvents);
        }

        System.out.println("PlaceOrderHandler received event: " + placeOrderEvent);
    }
}
