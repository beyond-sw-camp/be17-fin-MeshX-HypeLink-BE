package com.example.apiitem.item.usecase.port.in;

import MeshX.common.Page.PageRes;
import com.example.apiitem.item.usecase.port.in.request.*;
import com.example.apiitem.item.usecase.port.out.response.ItemInfoDto;
import org.springframework.data.domain.Pageable;

public interface WebPort {
    ItemInfoDto findItemById(Integer id);
    ItemInfoDto findItemsByItemCode(String code);

    PageRes<ItemInfoDto> findItemsWithPaging(Pageable pageable, String keyWord, String category);

    void saveItem(CreateItemCommand dto);

    void updateContents(UpdateItemContentCommand dto);
    void updateEnName(UpdateItemEnNameCommand dto);
    void updateKoName(UpdateItemKoNameCommand dto);
    void updateAmount(UpdateItemAmountCommand dto);
    void updateUnitPrice(UpdateItemUnitPriceCommand dto);
    void updateCompany(UpdateItemCompanyCommand dto);
    void updateCategory(UpdateItemCategoryCommand dto);
    void updateImages(UpdateItemImagesCommand dto);
}
