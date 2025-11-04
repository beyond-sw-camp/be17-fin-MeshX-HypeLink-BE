package MeshX.HypeLink.head_office.item.model.dto.response;

import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.image.model.dto.response.ImageUploadResponse;
import MeshX.HypeLink.image.model.entity.Image;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Builder
public class ItemInfoRes {
    private Integer id;
    private String itemCode; // 아이템 코드
    private String enName;
    private String koName;
    private String category;
    private Integer amount; // 가격
    private Integer unitPrice; // 원가
    private String content; // 아이템 설명
    private String company; // 회사
    private List<ItemImageInfoRes> images;

    public static ItemInfoRes toDto(Item item, Function<Image, String> urlGenerator) {
        List<ItemImageInfoRes> imageDtos = item.getItemImages().stream()
                .map(image -> ItemImageInfoRes.builder()
                        .id(image.getImage().getId())
                        .originalName(image.getImage().getOriginalFilename())
                        .imageUrl(urlGenerator.apply(image.getImage()))
                        .imageSize(image.getImage().getFileSize())
                        .index(image.getSortIndex())
                        .originalFilename(image.getImage().getOriginalFilename())
                        .build())
                .collect(Collectors.toList());

        return ItemInfoRes.builder()
                .id(item.getId())
                .enName(item.getEnName())
                .koName(item.getKoName())
                .category(item.getCategory().getCategory())
                .amount(item.getAmount())
                .unitPrice(item.getUnitPrice())
                .content(item.getContent())
                .company(item.getCompany())
                .itemCode(item.getItemCode())
                .images(imageDtos)
                .build();
    }
}
