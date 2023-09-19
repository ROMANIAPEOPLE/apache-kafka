package com.kafka.study.토픽조회;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Collections;
import java.util.Map;

@Configuration
public class LookupTopic {

    /**
     * 브로커 내 토픽 정보 조회
     */

    @Bean
    public AdminClient adminClient(KafkaAdmin kafkaAdmin) {
        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Bean
    public ApplicationRunner runner(AdminClient adminClient) {
        return args -> {


            Map<String, TopicListing> topicListingMap = adminClient.listTopics().namesToListings().get();

            for (String topicName : topicListingMap.keySet()) {
                TopicListing topicListing = topicListingMap.get(topicName);
                System.out.println(topicListing);

                Map<String, TopicDescription> stringTopicDescriptionMap = adminClient.describeTopics(Collections.singleton(topicName)).all().get();
                System.out.println(stringTopicDescriptionMap);
            }
        };
    }
}
