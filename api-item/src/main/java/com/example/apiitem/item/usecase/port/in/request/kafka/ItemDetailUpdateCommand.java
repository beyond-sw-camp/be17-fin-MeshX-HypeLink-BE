package com.example.apiitem.item.usecase.port.in.request.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDetailUpdateCommand {
    private Integer id;
    private Integer stock;
}
