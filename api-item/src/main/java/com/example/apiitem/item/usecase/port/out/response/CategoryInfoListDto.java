package com.example.apiitem.item.usecase.port.out.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoryInfoListDto {
    private List<CategoryInfoDto> categories;
}
