package com.example.apiauth.usecase.port.out.kafka;

import com.example.apiauth.domain.kafka.MemberRegisterEvent;

public interface EventPublisherPort {
    void publishMemberRegistered(MemberRegisterEvent event);
}
