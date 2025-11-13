package com.example.apiitem.item.usecase.port.in.request.kafka;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KafkaItemCommand {
    private Integer id;
    private Integer category;
    private String itemCode; // 아이템 코드
    private Integer unitPrice;       // 단가
    private Integer amount; // 가격
    private String enName; // 이름
    private String koName; // 이름
    private String content; // 아이템 설명
    private String company; // 회사

    private List<KafkaItemImageCommand> itemImages;
    private List<KafkaItemDetailCommand> itemDetails;
}
