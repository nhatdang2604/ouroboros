package org.nhatdang2604.configs;

import lombok.Getter;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${order-service.place-order.topic.name}")
    @Getter
    private String placeOrderTopicName;

    @Value(value = "${order-service.place-order.topic.num-partition}")
    private int placeOrderTopicPartitions;

    @Value(value = "${order-service.place-order.topic.replication-factor}")
    private short placeOrderTopicRepFactor;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic placeOrderTopic() {
        return new NewTopic(placeOrderTopicName, placeOrderTopicPartitions, placeOrderTopicRepFactor);
    }
}