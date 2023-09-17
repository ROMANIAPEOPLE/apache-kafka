package com.kafka.study.consumer;

import com.kafka.study.model.Animal;
import jakarta.validation.Valid;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TestConsumer {
    @KafkaListener(id = "clip4-listener-id", topics = "clip4-listener")
    public void listen(String message,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
                       @Header(KafkaHeaders.PARTITION) int partition,
                       @Header(KafkaHeaders.OFFSET) long offset,
                       ConsumerRecordMetadata metadata) {
        System.out.println("Listener. offset=" + metadata.offset() +
                ", partition=" + partition +
                ", timestamp=" + new Date(timestamp) +
                ", message=" + message
        );
    }

    @KafkaListener(id = "clip4-animal-listener", topics = "clip4-animal", containerFactory = "kafkaJsonContainerFactory")
    public void listenAnimal(@Valid Animal animal) {
        System.out.println("Animal. animal=" + animal);
    }
}
