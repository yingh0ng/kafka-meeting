package com.joy.kafka.meetingassistant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;

@Configuration
public class ProducerConfiguration {

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){
        HashMap<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "127.0.0.1:9092");
        configs.put("retries", 1);
        configs.put("acks", "all");
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        DefaultKafkaProducerFactory<String, Object> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(configs);

        return new KafkaTemplate<>(kafkaProducerFactory);
    }
}
