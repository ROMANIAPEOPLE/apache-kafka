package com.kafka.study.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TestProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RoutingKafkaTemplate routingKafkaTemplate;

    public TestProducer(KafkaTemplate<String, String> kafkaTemplate, RoutingKafkaTemplate routingKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.routingKafkaTemplate = routingKafkaTemplate;
    }

    /**
     * Spring boot 2.x 버전에서 사용되던 kafkaTemplate.send 의 return 값인 listenableFuture 가 deprecated 처리됨.
     */

    public void async(String topic, String message) {
        CompletableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(topic, message);

        // whenComplete : 성공/실패 여부와 관계 없이 항상 실행
        sendResult.whenComplete((stringStringSendResult, throwable) -> {
                    if (throwable == null) {
                        System.out.println("send success");
                    } else {
                        throwable.printStackTrace();
                    }
                }
        );
    }

    public void asyncByRoutingKafkaTemplate(String topic, String message) {
        routingKafkaTemplate.send(topic, message);
    }

    public void asyncByRoutingKafkaTemplate(String topic, byte[] message) {
        routingKafkaTemplate.send(topic, message);
    }
}

