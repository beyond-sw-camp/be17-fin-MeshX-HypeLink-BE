package com.example.apiitem.item.usecase.port.in;

import com.example.apiitem.item.usecase.port.out.response.SizeInfoListDto;

public interface SizeWebPort {
    SizeInfoListDto findAll();
}
