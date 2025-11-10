package org.example.apidirect.item.usecase.port.in.command;

import lombok.Builder;
import lombok.Getter;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaItemImageDto;
import org.example.apidirect.item.domain.StoreItemImage;

@Getter
@Builder
public class SyncItemImageCommand {
    private Integer sortIndex;
    private String originalFilename;
    private String savedPath;
    private String contentType;
    private Long fileSize;

    public static SyncItemImageCommand toCommand(KafkaItemImageDto dto) {
        return SyncItemImageCommand.builder()
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
