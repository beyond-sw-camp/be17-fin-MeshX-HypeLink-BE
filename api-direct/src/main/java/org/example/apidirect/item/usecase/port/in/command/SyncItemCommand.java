package org.example.apidirect.item.usecase.port.in.command;

import lombok.Builder;
import lombok.Getter;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaItemDto;
import org.example.apidirect.item.domain.StoreItem;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class SyncItemCommand {
    private String itemCode;
    private Integer unitPrice;
    private Integer amount;
    private String enName;
    private String koName;
    private String content;
    private String company;
    private String category;

    private List<SyncItemDetailCommand> itemDetails;
    private List<SyncItemImageCommand> itemImages;

    public static SyncItemCommand toCommand(KafkaItemDto dto) {
        return SyncItemCommand.builder()
                .itemCode(dto.getItemCode())
                .unitPrice(dto.getUnitPrice())
                .amount(dto.getAmount())
                .enName(dto.getEnName())
                .koName(dto.getKoName())
                .content(dto.getContent())
                .company(dto.getCompany())
                .category(String.valueOf(dto.getCategory()))
                .itemDetails(dto.getItemDetails().stream()
                        .map(SyncItemDetailCommand::toCommand)
                        .collect(Collectors.toList()))
                .itemImages(dto.getItemImages().stream()
                        .map(SyncItemImageCommand::toCommand)
                        .collect(Collectors.toList()))
                .build();
    }

    public StoreItem toDomain() {
        return StoreItem.builder()
                .itemCode(itemCode)
                .unitPrice(unitPrice)
                .amount(amount)
                .enName(enName)
                .koName(koName)
                .content(content)
                .company(company)
                .category(category)
                .itemDetails(itemDetails.stream()
                        .map(SyncItemDetailCommand::toDomain)
                        .collect(Collectors.toList()))
                .itemImages(itemImages.stream()
                        .map(SyncItemImageCommand::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }
}
