package com.kafka.study.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

/**
 * 컨슈머가 특정 레코드를 전달 받았는지 프로듀서가 레코드를 전달 한 시점에 확인할 수 있음
 */

@Configuration
public class ReplyingKafkaTemplateConfiguration {

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(ProducerFactory<String, String> producerFactory, ConcurrentMessageListenerContainer<String, String> replyListenerContainer) {

        return new ReplyingKafkaTemplate<>(producerFactory, replyListenerContainer);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, String> replyListenerContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        return factory.createContainer("test-reply");
    }
}
