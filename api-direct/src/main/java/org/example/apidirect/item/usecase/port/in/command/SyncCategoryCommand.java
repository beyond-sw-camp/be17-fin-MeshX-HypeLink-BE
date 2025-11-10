package org.example.apidirect.item.usecase.port.in.command;

import lombok.Builder;
import lombok.Getter;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaCategoryList;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class SyncCategoryCommand {
    private List<String> categories;

    public static SyncCategoryCommand toCommand(KafkaCategoryList dto) {
        return SyncCategoryCommand.builder()
                .categories(dto.getCategories().stream()
                        .map(cat -> cat.getCategory())
                        .collect(Collectors.toList()))
                .build();
    }
}
