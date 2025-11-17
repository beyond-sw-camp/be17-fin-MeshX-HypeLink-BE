package com.example.apidirect.item.adapter.in.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveStoreItemListRequest {
    private Integer storeId;
    private List<SaveStoreItemRequest> items;
}
