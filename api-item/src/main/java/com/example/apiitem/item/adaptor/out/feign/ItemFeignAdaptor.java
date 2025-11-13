package com.example.apiitem.item.adaptor.out.feign;

import MeshX.common.EventOutAdapter;
import com.example.apiitem.item.adaptor.out.feign.dto.SaveItemDetailReq;
import com.example.apiitem.item.adaptor.out.feign.dto.SaveItemReq;
import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.usecase.port.in.request.CreateItemCommand;
import com.example.apiitem.item.usecase.port.out.FeignItemPort;
import com.example.apiitem.util.ItemDetailMapper;
import com.example.apiitem.util.ItemMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@EventOutAdapter
@RequiredArgsConstructor
public class ItemFeignAdaptor implements FeignItemPort {
    private final ItemFeignClient itemFeignClient;

    @Override
    public String saveItem(Item domain, CreateItemCommand command) {
        List<SaveItemDetailReq> itemDetailReqs = domain.getItemDetails().stream()
                .map(ItemDetailMapper::toFeignDto)
                .toList();
        SaveItemReq dto = ItemMapper.toFeignDto(domain, itemDetailReqs);

        return itemFeignClient.saveItem(dto);
    }

    @Override
    public String validateItem(String itemCode) {
        return itemFeignClient.validateItem(itemCode);
    }
}
