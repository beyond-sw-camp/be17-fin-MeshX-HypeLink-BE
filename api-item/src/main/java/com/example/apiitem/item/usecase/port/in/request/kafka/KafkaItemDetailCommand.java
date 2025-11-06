package com.example.apiitem.item.usecase.port.in.request.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaItemDetailCommand {
    private Integer id;
    private Integer colorId;
    private Integer sizeId;
    private String itemDetailCode;
    private Integer stock;
}
