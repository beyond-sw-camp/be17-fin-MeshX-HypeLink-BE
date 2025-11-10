package org.example.apidirect.item.usecase.port.in.command;

import lombok.Builder;
import lombok.Getter;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaColor;
import org.example.apidirect.item.domain.Color;

@Getter
@Builder
public class SyncColorCommand {
    private Integer id;
    private String colorName;
    private String colorCode;

    public static SyncColorCommand toCommand(KafkaColor dto) {
        return SyncColorCommand.builder()
                .id(dto.getId())
                .colorName(dto.getColorName())
                .colorCode(dto.getColorCode())
                .build();
    }

    public Color toDomain() {
        return Color.builder()
                .id(id)
                .colorName(colorName)
                .colorCode(colorCode)
                .build();
    }
}
