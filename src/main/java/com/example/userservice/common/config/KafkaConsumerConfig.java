//package com.example.userservice.common.config;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Configuration
//@EnableKafka
//public class KafkaConsumerConfig {
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupId;
//
//    @Bean
//    public <T> ConsumerFactory<String, T> consumerFactory(Class<T> targetType) {
//        JsonDeserializer<T> deserializer = new JsonDeserializer<>(targetType, false);
//        deserializer.addTrustedPackages("*"); // Allow deserialization of all packages
//
//        return new DefaultKafkaConsumerFactory<>(
//                kafkaConsumerProperties(),
//                new StringDeserializer(),
//                new ErrorHandlingDeserializer<>(deserializer)
//        );
//    }
//
//    @Bean
//    public <T> ConcurrentKafkaListenerContainerFactory<String, T> kafkaListenerContainerFactory(Class<T> targetType) {
//        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory(targetType));
//        return factory;
//    }
//
//    private Map<String, Object> kafkaConsumerProperties() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        // Other properties
//        return props;
//    }
//}
