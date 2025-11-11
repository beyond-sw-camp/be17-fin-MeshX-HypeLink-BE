package com.example.apiitem.item.usecase.port.in;

import com.example.apiitem.item.adaptor.out.feign.dto.SaveItemDetailsReq;
import com.example.apiitem.item.usecase.port.in.request.kafka.ItemDetailUpdateCommand;

public interface KafkaItemDetailInPort {
    void updateItemDetail(ItemDetailUpdateCommand command);

    void rollback(SaveItemDetailsReq command);
}
