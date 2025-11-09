package org.example.apidirect.item.adapter.in.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaItemDetailDto {
    private Integer id;
    private Integer colorId;
    private Integer sizeId;
    private String itemDetailCode;
    private Integer stock;
}
