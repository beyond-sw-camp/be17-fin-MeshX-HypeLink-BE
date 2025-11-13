package MeshX.HypeLink.head_office.item.service.kafka;

import MeshX.HypeLink.head_office.item.model.entity.ItemImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KafkaItemImageDto {
    private Integer id;
    private Integer sortIndex;
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private Long fileSize;

    public static KafkaItemImageDto toDto(ItemImage entity) {
        return KafkaItemImageDto.builder()
                .id(entity.getId())
                .sortIndex(entity.getSortIndex())
                .originalFilename(entity.getImage().getOriginalFilename())
                .savedPath(entity.getImage().getSavedPath())
                .contentType(entity.getImage().getContentType())
                .fileSize(entity.getImage().getFileSize())
                .build();
    }
}
