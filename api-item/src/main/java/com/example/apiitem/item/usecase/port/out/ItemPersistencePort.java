package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Category;
import com.example.apiitem.item.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemPersistencePort {
    Item findById(Item domain);
    Item findByItemCode(Item domain);

    Page<Item> findItemsWithPaging(Pageable pageable, String keyWord, String category);

    boolean isExist(Item domain);
    Item save(Item item);
    void updateContents(Integer itemId, String content);
    void updateEnName(Integer itemId, String enName);
    void updateKoName(Integer itemId, String koName);
    void updateAmount(Integer itemId, Integer amount);
    void updateUnitPrice(Integer itemId, Integer unitPrice);
    void updateCompany(Integer itemId, String company);
    void updateCategory(Integer itemId, Category category);
}
