package com.example.apiauth.usecase.port.out.usecase;

import com.example.apiauth.domain.kafka.MemberRegisterEvent;

public interface EventPublisherPort {
    void publishMemberRegistered(MemberRegisterEvent event);
}
