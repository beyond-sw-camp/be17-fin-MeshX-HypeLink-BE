package org.example.apidirect.item.usecase.port.in.request.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaItemDetailCommand {
    private Integer id;
    private Integer colorId;
    private Integer sizeId;
    private String itemDetailCode;
    private Integer stock;
}
