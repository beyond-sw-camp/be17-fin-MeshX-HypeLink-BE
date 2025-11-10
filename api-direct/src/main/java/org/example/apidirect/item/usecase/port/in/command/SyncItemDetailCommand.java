package org.example.apidirect.item.usecase.port.in.command;

import lombok.Builder;
import lombok.Getter;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaItemDetailDto;
import org.example.apidirect.item.domain.StoreItemDetail;

@Getter
@Builder
public class SyncItemDetailCommand {
    private String itemDetailCode;
    private Integer colorId;
    private Integer sizeId;
    private Integer stock;

    public static SyncItemDetailCommand toCommand(KafkaItemDetailDto dto) {
        return SyncItemDetailCommand.builder()
                .itemDetailCode(dto.getItemDetailCode())
                .colorId(dto.getColorId())
                .sizeId(dto.getSizeId())
                .stock(0)  // 가맹점 재고는 항상 0으로 초기화
                .build();
    }

    public StoreItemDetail toDomain() {
        return StoreItemDetail.builder()
                .itemDetailCode(itemDetailCode)
                .colorId(colorId)
                .sizeId(sizeId)
                .stock(stock)
                .build();
    }
}
