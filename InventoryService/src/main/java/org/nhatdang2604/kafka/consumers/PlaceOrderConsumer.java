package org.nhatdang2604.kafka.consumers;

import com.lmax.disruptor.RingBuffer;
import org.nhatdang2604.events.KafkaSendable;
import org.nhatdang2604.events.PlaceOrderEvent;
import org.nhatdang2604.lmax.slots.RingBufferSlot;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceOrderConsumer {

    private final RingBuffer<RingBufferSlot> ringBuffer;

    public PlaceOrderConsumer(
            @Qualifier("placeOrderRingBuffer") RingBuffer<RingBufferSlot> ringBuffer
    ) {
        this.ringBuffer = ringBuffer;
    }

    @KafkaListener(
        topics = "${queue.place-order.topic.name}",
        groupId = "${spring.kafka.consumer.group-id}",
        batch = "true"
    )
    public void batchConsume(List<KafkaSendable> rawEvents) {

        for (KafkaSendable rawEvent: rawEvents) {
            PlaceOrderEvent event = (PlaceOrderEvent) rawEvent;
            System.out.println("Recieved at consumer: " + event);

            //Claim the next sequence number from the RingBuffer
            long sequence = ringBuffer.next();

            try {

                RingBufferSlot slot = ringBuffer.get(sequence);
                slot.setSlot(event);

            } finally {

                //Commit the sequence to indicate the mesasge has been handled
                ringBuffer.publish(sequence);
            }
        }
    }
}
