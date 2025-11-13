package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemDetail;

import java.util.List;

public interface KafkaItemOutPort {
    String saveItem(Item domain, List<ItemDetail> itemDetailList);
    String saveItemDetail(Item item, List<ItemDetail> itemDetails);
}
