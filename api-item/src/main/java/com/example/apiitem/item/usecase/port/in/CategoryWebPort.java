package com.example.apiitem.item.usecase.port.in;

import com.example.apiitem.item.usecase.port.out.response.CategoryInfoListDto;

public interface CategoryWebPort {
    CategoryInfoListDto findAll();
}
