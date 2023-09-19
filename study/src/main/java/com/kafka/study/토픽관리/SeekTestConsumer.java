package com.kafka.study.토픽관리;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.stereotype.Service;

/**
 * 특정 토픽의 파티션 오프셋을 읽어올 수 있음
 */

@Service
public class SeekTestConsumer extends AbstractConsumerSeekAware {
    @KafkaListener(id = "test--id", topics = "test-topic")
    public void listen(String message) {
        System.out.println("message=" + message);
    }

    public void seek() {
        getSeekCallbacks().forEach((tp, consumerSeekCallback) -> consumerSeekCallback.seek(tp.topic(), tp.partition(), 0));
    }
}
