package org.example.apidirect.item.adapter.in.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaCategoryList;
import org.example.apidirect.item.adapter.in.kafka.dto.KafkaEnvelope;
import org.example.apidirect.item.usecase.port.in.SyncCategoryUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryKafkaConsumer {

    private final SyncCategoryUseCase syncCategoryUseCase;

    @KafkaListener(topics = "category.sync.1", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCategory(KafkaEnvelope<KafkaCategoryList> envelope) {
        try {
            if (!"CATEGORY".equals(envelope.getType())) {
                return;
            }

            KafkaCategoryList payload = envelope.getPayload();
            List<String> categories = payload.getCategories().stream()
                    .map(cat -> cat.getCategory())
                    .collect(Collectors.toList());


            syncCategoryUseCase.syncCategories(categories);

        } catch (Exception e) {
        }
    }
}
