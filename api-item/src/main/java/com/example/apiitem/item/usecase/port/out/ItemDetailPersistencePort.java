package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemDetail;

import java.util.List;

public interface ItemDetailPersistencePort {
    void saveAll(List<ItemDetail> itemDetails, Item entity);
}
