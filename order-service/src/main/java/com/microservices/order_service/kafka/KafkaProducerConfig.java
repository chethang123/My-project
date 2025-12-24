package com.microservices.order_service.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, OrderEvent> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-619z3.us-east1.gcp.confluent.cloud:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // SASL/SSL for Confluent Cloud
        config.put("security.protocol", "SASL_SSL");
        config.put("sasl.mechanism", "PLAIN");
        config.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='VXVGOHJ5C5UWZZZN' password='cfltZtJ3ma0KKm/wpsbJlQNo6za7vUyveWA8Cksh4EmJ7AuaefepnQbiaRVJvY1A';");

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, OrderEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
