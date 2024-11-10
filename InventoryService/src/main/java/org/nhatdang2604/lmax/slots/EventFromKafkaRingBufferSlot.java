package org.nhatdang2604.lmax.slots;

import org.nhatdang2604.events.KafkaSendable;

public class EventFromKafkaRingBufferSlot implements RingBufferSlot {

    private KafkaSendable slot;

    public EventFromKafkaRingBufferSlot(KafkaSendable slot) {
        this.slot = slot;
    }

    @Override
    public KafkaSendable getSlot() {
        return slot;
    }

    @Override
    public void setSlot(Object slot) {
        this.slot = (KafkaSendable) slot;
    }

}
