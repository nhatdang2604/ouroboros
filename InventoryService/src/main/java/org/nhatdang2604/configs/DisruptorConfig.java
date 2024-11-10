package org.nhatdang2604.configs;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.nhatdang2604.constants.EventConstant;
import org.nhatdang2604.lmax.factories.KafkaEventFactory;
import org.nhatdang2604.lmax.factories.EventHandlerFactory;
import org.nhatdang2604.lmax.slots.RingBufferSlot;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class DisruptorConfig {

    @Value("${place-order.ring-buffer.size}")
    private int placeOrderRingBufferSize;

    @Bean
    protected EventHandlerFactory eventHandlerFactory() {
        return new EventHandlerFactory();
    }

    @Bean(name = "placeOrderEventFactory")
    protected EventFactory<RingBufferSlot> kafkaPlaceOrderEventFactory() {
        KafkaEventFactory factory = new KafkaEventFactory();
        factory.setType(EventConstant.TYPE_PLACE_ORDER);
        return factory;
    };

    @Bean(name = "placeOrderDisruptor")
    public Disruptor<RingBufferSlot> placeOrderDisruptor(
            EventHandlerFactory eventHandlerFactory,
            @Qualifier("placeOrderEventFactory") EventFactory<RingBufferSlot> placeOrderEventFactory
    ) {

        //Make event
        Disruptor<RingBufferSlot> disruptor = new Disruptor<>(
            placeOrderEventFactory,
            placeOrderRingBufferSize,
            Executors.defaultThreadFactory(),
            ProducerType.SINGLE,
            new BusySpinWaitStrategy()
        );

        //Make handler
        EventHandler<RingBufferSlot> handler = eventHandlerFactory.make(EventConstant.TYPE_PLACE_ORDER);
        disruptor.handleEventsWith(handler);

        return disruptor;
    }

    @Bean(name = "placeOrderRingBuffer")
    public RingBuffer<RingBufferSlot> placeOrderRingBuffer(
            @Qualifier("placeOrderDisruptor") Disruptor<RingBufferSlot> placeOrderDisruptor
    ) {
        return placeOrderDisruptor.getRingBuffer();
    }
}
