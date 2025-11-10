package org.example.apidirect.item.usecase.port.in.request.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaCategoryCommand {
    private Integer id;
    private String category;
}
