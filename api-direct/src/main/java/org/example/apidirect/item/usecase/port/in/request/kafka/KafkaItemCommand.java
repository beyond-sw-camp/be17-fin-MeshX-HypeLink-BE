package org.example.apidirect.item.usecase.port.in.request.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaItemCommand {
    private Integer id;
    private Integer category;
    private String itemCode;
    private Integer unitPrice;
    private Integer amount;
    private String enName;
    private String koName;
    private String content;
    private String company;

    private List<KafkaItemImageCommand> itemImages;
    private List<KafkaItemDetailCommand> itemDetails;
}
