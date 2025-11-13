package org.example.apidirect.item.usecase.port.in.request.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaColorListCommand {
    private List<KafkaColorCommand> colors;
}
