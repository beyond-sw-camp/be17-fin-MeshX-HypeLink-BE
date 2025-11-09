package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemDetailPersistencePort {
    void saveAll(List<ItemDetail> itemDetails, Item entity);
    void saveAllWithId(List<ItemDetail> itemDetails, Item entity);

    ItemDetail findById(Integer id);
    ItemDetail findByItemDetailCode(String detailCode);
    List<ItemDetail> findByItemId(Integer id);
}
