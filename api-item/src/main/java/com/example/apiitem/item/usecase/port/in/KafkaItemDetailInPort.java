package com.example.apiitem.item.usecase.port.in;

import com.example.apiitem.item.usecase.port.in.request.kafka.ItemDetailUpdateCommand;

public interface KafkaItemDetailInPort {
    void updateItemDetail(ItemDetailUpdateCommand command);
}
