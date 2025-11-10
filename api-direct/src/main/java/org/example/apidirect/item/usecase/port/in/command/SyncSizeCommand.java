package org.example.apidirect.item.usecase.port.in.command;

import lombok.Builder;
import lombok.Getter;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaSize;
import org.example.apidirect.item.domain.Size;

@Getter
@Builder
public class SyncSizeCommand {
    private Integer id;
    private String size;

    public static SyncSizeCommand toCommand(KafkaSize dto) {
        return SyncSizeCommand.builder()
                .id(dto.getId())
                .size(dto.getSize())
                .build();
    }

    public Size toDomain() {
        return Size.builder()
                .id(id)
                .size(size)
                .build();
    }
}
