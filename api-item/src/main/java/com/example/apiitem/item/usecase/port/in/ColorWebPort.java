package com.example.apiitem.item.usecase.port.in;

import com.example.apiitem.item.usecase.port.out.response.ColorInfoListDto;

public interface ColorWebPort {
    ColorInfoListDto findAll();
}
