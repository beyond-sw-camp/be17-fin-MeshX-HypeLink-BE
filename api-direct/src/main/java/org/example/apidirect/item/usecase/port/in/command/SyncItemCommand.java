package org.example.apidirect.item.usecase.port.in.command;

import lombok.Builder;
import lombok.Getter;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaItemDto;
import org.example.apidirect.item.domain.StoreItem;
import org.example.apidirect.item.domain.StoreItemDetail;
import org.example.apidirect.item.domain.StoreItemImage;

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

    private List<ItemDetailCommand> itemDetails;
    private List<ItemImageCommand> itemImages;

    public static SyncItemCommand from(KafkaItemDto dto) {
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
                        .map(ItemDetailCommand::from)
                        .collect(Collectors.toList()))
                .itemImages(dto.getItemImages().stream()
                        .map(ItemImageCommand::from)
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
                        .map(ItemDetailCommand::toDomain)
                        .collect(Collectors.toList()))
                .itemImages(itemImages.stream()
                        .map(ItemImageCommand::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Builder
    public static class ItemDetailCommand {
        private String itemDetailCode;
        private String color;
        private String colorCode;
        private String size;
        private Integer stock;

        public static ItemDetailCommand from(org.example.apidirect.item.adapter.in.kafka.dto.KafkaItemDetailDto dto) {
            return ItemDetailCommand.builder()
                    .itemDetailCode(dto.getItemDetailCode())
                    .stock(0)  // ⭐ 가맹점 재고는 항상 0으로 초기화
                    .build();
        }

        public StoreItemDetail toDomain() {
            return StoreItemDetail.builder()
                    .itemDetailCode(itemDetailCode)
                    .color(color)
                    .colorCode(colorCode)
                    .size(size)
                    .stock(stock)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ItemImageCommand {
        private Integer sortIndex;
        private String originalFilename;
        private String savedPath;
        private String contentType;
        private Long fileSize;

        public static ItemImageCommand from(org.example.apidirect.item.adapter.in.kafka.dto.KafkaItemImageDto dto) {
            return ItemImageCommand.builder()
                    .sortIndex(dto.getSortIndex())
                    .originalFilename(dto.getOriginalFilename())
                    .savedPath(dto.getSavedPath())
                    .contentType(dto.getContentType())
                    .fileSize(dto.getFileSize())
                    .build();
        }

        public StoreItemImage toDomain() {
            return StoreItemImage.builder()
                    .sortIndex(sortIndex)
                    .originalFilename(originalFilename)
                    .savedPath(savedPath)
                    .contentType(contentType)
                    .fileSize(fileSize)
                    .build();
        }
    }
}
