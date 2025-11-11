package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.usecase.port.in.request.CreateItemCommand;

public interface FeignItemPort {
    String saveItem(Item domain, CreateItemCommand command);
    String validateItem(String itemCode);
}
