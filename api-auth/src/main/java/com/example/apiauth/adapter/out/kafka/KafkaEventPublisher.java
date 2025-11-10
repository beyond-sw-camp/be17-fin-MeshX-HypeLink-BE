package com.example.apiauth.adapter.out.kafka;

import com.example.apiauth.domain.kafka.MemberRegisterEvent;
import com.example.apiauth.usecase.port.out.usecase.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisher implements EventPublisherPort {

    private final KafkaTemplate<String, MemberRegisterEvent> kafkaTemplate;
    private static final String TOPIC = "member-registered";

    @Override
    public void publishMemberRegistered(MemberRegisterEvent event) {
        try {
            kafkaTemplate.send(TOPIC, event.getMemberId().toString(), event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish event", e);
        }
    }
}
