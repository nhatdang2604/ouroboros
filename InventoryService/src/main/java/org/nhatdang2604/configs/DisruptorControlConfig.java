package org.nhatdang2604.configs;

import com.lmax.disruptor.dsl.Disruptor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.nhatdang2604.lmax.slots.RingBufferSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisruptorControlConfig {

    private final Disruptor<RingBufferSlot> placeOrderDisruptor;

    @Autowired
    public DisruptorControlConfig(DisruptorConfig disruptorConfig) {
        this.placeOrderDisruptor = disruptorConfig.placeOrderDisruptor(
                disruptorConfig.eventHandlerFactory(),
                disruptorConfig.kafkaPlaceOrderEventFactory()
        );
    }


    @PostConstruct
    public void startDisruptor() {
        placeOrderDisruptor.start();
    }

    @PreDestroy
    public void stopDisruptor() {
        placeOrderDisruptor.shutdown();
    }

}
