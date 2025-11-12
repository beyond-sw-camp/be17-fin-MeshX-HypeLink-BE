package com.example.apiitem.item.usecase.port.in.request.kafka;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KafkaSizeList {
    private List<KafkaSize> sizes;
}
