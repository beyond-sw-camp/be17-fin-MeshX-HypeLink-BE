package com.example.apidirect.item.usecase.port.in;

import com.example.apidirect.item.adapter.in.web.dto.request.SaveStoreCategoriesRequest;
import com.example.apidirect.item.adapter.in.web.dto.request.SaveStoreItemListRequest;

public interface ItemCommandPort {
    void updateStock(Integer storeId, String itemDetailCode, Integer stockChange);

    void saveAllItemsFromHeadOffice(SaveStoreItemListRequest request);

    void saveAllCategoriesFromHeadOffice(SaveStoreCategoriesRequest request);
}
