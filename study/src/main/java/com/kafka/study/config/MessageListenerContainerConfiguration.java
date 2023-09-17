package com.kafka.study.config;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.kafka.study.consumer.DefaultMessageListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessageListenerContainerConfiguration {

    /**
     * @KafkaListener 를 사용하지 않고 KafkaMessageListenerContainer 를 직접 정의하여 consumer 실행 및 관리
     */

    @Bean
    public KafkaMessageListenerContainer<String, String> kafkaMessageListenerContainer() {
        ContainerProperties containerProps = new ContainerProperties("test-consumer");
        containerProps.setGroupId("test-container");
        containerProps.setAckMode(ContainerProperties.AckMode.BATCH);

        // 리스너가 존재하지 않으면 consumer 가 동작하지 않는다.
        containerProps.setMessageListener(new DefaultMessageListener());

        KafkaMessageListenerContainer<String, String> container = new KafkaMessageListenerContainer<>(containerFactory(), containerProps);
        container.setAutoStartup(false);

        return container;
    }

    private ConsumerFactory<String, String> containerFactory() {
        return new DefaultKafkaConsumerFactory<>(props());
    }

    private Map<String, Object> props() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return props;
    }
}