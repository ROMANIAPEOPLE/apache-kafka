package com.kafka.study.토픽생성;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name("test").build();
    }

    /**
     * 여러개의 토픽을 생성할 때
     * 토픽의 파티션 개수, 복제 개수, retention inverval 등 설정
     * @return
     */

    @Bean
    public KafkaAdmin.NewTopics createTopics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("test").build(),
                TopicBuilder.name("test2")
                        .partitions(3)
                        .replicas(1)
                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000 * 60 * 60 * 24))
                        .build()

        );
    }

}
