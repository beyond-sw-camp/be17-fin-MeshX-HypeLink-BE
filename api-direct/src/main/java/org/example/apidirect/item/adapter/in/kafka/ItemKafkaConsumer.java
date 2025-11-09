package org.example.apidirect.item.adapter.in.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaEnvelope;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaItemDto;
import org.example.apidirect.item.usecase.port.in.SyncItemUseCase;
import org.example.apidirect.item.usecase.port.in.command.SyncItemCommand;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemKafkaConsumer {

    private final SyncItemUseCase syncItemUseCase;

    @KafkaListener(topics = "items.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeItem(KafkaEnvelope<KafkaItemDto> envelope) {
        try {
            if (!"ITEM".equals(envelope.getType())) {
                return;
            }

            KafkaItemDto payload = envelope.getPayload();

            // Kafka DTO → Command 변환
            SyncItemCommand command = SyncItemCommand.from(payload);

            // UseCase 실행
            syncItemUseCase.syncItem(command);

        } catch (Exception e) {
            log.error("[KAFKA] Item sync failed: {}", e.getMessage(), e);
            // TODO: DLQ 처리 또는 재시도 로직
        }
    }
}
