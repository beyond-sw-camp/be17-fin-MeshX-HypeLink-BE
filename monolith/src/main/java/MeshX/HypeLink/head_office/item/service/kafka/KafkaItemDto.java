package MeshX.HypeLink.head_office.item.service.kafka;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KafkaItemDto {
    private Integer id;
    private Integer category;
    private String itemCode; // 아이템 코드
    private Integer unitPrice;       // 단가
    private Integer amount; // 가격
    private String enName; // 이름
    private String koName; // 이름
    private String content; // 아이템 설명
    private String company; // 회사

    private List<KafkaItemImageDto> itemImages;
    private List<KafkaItemDetailDto> itemDetails;

    public static KafkaItemDto toDto(Item entity) {
        List<KafkaItemImageDto> itemImageDtos = entity.getItemImages().stream()
                .map(KafkaItemImageDto::toDto)
                .toList();

        List<KafkaItemDetailDto> itemDetailDtos = entity.getItemDetails().stream()
                .map(KafkaItemDetailDto::toDto)
                .toList();

        return KafkaItemDto.builder()
                .id(entity.getId())
                .category(entity.getCategory().getId())
                .itemCode(entity.getItemCode())
                .unitPrice(entity.getUnitPrice())
                .amount(entity.getAmount())
                .enName(entity.getEnName())
                .koName(entity.getKoName())
                .content(entity.getContent())
                .company(entity.getCompany())
                .itemDetails(itemDetailDtos)
                .itemImages(itemImageDtos)
                .build();
    }
}
