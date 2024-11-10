package org.nhatdang2604.lmax.handlers;

import com.lmax.disruptor.EventHandler;
import org.nhatdang2604.lmax.slots.RingBufferSlot;

public class BaseEventHandler implements EventHandler<RingBufferSlot> {

    @Override
    public void onEvent(RingBufferSlot slot, long l, boolean b) throws Exception {
        //Do nothing
    }
}
