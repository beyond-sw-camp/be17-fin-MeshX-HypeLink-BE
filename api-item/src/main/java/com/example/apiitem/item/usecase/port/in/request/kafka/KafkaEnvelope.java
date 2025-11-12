package com.example.apiitem.item.usecase.port.in.request.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaEnvelope<T> {
    private String type;   // ex) "ITEM", "STORE", "ORDER"
    private T payload;     // 실제 DTO
}