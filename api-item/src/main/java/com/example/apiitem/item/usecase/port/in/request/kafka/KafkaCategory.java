package com.example.apiitem.item.usecase.port.in.request.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaCategory {
    private Integer id;
    private String category;
}
