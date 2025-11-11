package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.usecase.port.in.request.kafka.ItemDetailUpdateCommand;

public interface KafkaItemDetailOutPort {
    void updateItemDetail(ItemDetailUpdateCommand command);
}
