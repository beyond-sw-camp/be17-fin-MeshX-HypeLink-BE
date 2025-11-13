package com.example.apiitem.item.usecase.port.in.request.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaColor {
    private Integer id;
    private String colorName;
    private String colorCode;
}
