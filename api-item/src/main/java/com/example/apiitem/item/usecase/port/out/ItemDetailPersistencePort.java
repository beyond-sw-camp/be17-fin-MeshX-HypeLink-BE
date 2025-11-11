package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemDetail;
import com.example.apiitem.item.usecase.port.in.request.kafka.ItemDetailUpdateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemDetailPersistencePort {
    List<ItemDetail> saveAll(List<ItemDetail> itemDetails, Item entity);
    void saveAllWithId(List<ItemDetail> itemDetails, Item entity);

    void deleteAllWIthItemId(Integer itemId);

    ItemDetail findById(Integer id);
    ItemDetail findByItemDetailCode(String detailCode);
    List<ItemDetail> findByItemId(Integer id);

    void updateStock(ItemDetail command);
}
