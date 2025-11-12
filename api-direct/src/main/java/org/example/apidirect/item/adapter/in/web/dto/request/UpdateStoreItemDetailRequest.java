package org.example.apidirect.item.adapter.in.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoreItemDetailRequest {
    private String itemCode;
    private String itemDetailCode;
    private Integer updateStock;
}
