package org.example.apidirect.item.adapter.in.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaItemDto {
    private Integer id;
    private Integer category;
    private String itemCode;
    private Integer unitPrice;
    private Integer amount;
    private String enName;
    private String koName;
    private String content;
    private String company;

    private List<KafkaItemImageDto> itemImages;
    private List<KafkaItemDetailDto> itemDetails;
}
