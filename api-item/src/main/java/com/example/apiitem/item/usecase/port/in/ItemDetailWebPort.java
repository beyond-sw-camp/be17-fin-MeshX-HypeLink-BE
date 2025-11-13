package com.example.apiitem.item.usecase.port.in;

import com.example.apiitem.item.usecase.port.in.request.CreateItemDetailsCommand;
import com.example.apiitem.item.usecase.port.out.response.ItemAndItemDetailInfoDto;
import com.example.apiitem.item.usecase.port.out.response.ItemDetailsInfoListDto;

public interface ItemDetailWebPort {
    ItemAndItemDetailInfoDto findItemDetailById(Integer id);
    ItemAndItemDetailInfoDto findItemDetailByItemDetailCode(String detailCode);
    ItemDetailsInfoListDto findItemDetailsByItem(Integer id);

    void saveItemDetails(CreateItemDetailsCommand dto);
}
