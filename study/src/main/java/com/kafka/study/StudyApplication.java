package com.kafka.study;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class StudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner_topic() {
        return args -> {
            Properties properties = new Properties();
            properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//            properties.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "SSL");

            AdminClient adminClient = AdminClient.create(properties);
            System.out.println("ConsumerApplication is running.");

            DescribeTopicsResult describeResult = adminClient.describeTopics(Collections.singletonList("pocket-member-abnormalLogin"));

            try {
                TopicDescription topicDescription = describeResult.values().get("pocket-member-abnormalLogin").get();
                System.out.println("Number of partitions: " + topicDescription.partitions().size());

                System.out.println(topicDescription.name());
                System.out.println(topicDescription.topicId());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    public ApplicationRunner runner_consumer() {
        return args -> {
            Properties properties = new Properties();
            properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            properties.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "SSL");

            AdminClient adminClient = AdminClient.create(properties);
            System.out.println("ConsumerApplication is running.");

            DescribeConsumerGroupsResult describeResult = adminClient.describeConsumerGroups(Collections.singletonList("member-saveChangeEventHistory"));
            try {
                ConsumerGroupDescription groupDescription = describeResult.describedGroups().get("member-saveChangeEventHistory").get();
                System.out.println("Number of consumers: " + groupDescription.members().size());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
    }
}
