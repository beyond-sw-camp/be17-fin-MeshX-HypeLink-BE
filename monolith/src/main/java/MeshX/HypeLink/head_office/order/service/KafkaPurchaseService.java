package MeshX.HypeLink.head_office.order.service;

import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import MeshX.HypeLink.head_office.item.service.kafka.KafkaEnvelope;
import MeshX.HypeLink.head_office.order.service.dto.ItemDetailUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KafkaPurchaseService {
    @Value("${kafka.topic.itemDetail}")
    private String itemDetailTopic;

    private final KafkaTemplate<String, KafkaEnvelope> kafkaTemplate;

    public void syncItemDetailStock(ItemDetail entity, Integer quantity) {
        ItemDetailUpdateDto dto = ItemDetailUpdateDto.toDtoPlus(entity, quantity);

        KafkaEnvelope<ItemDetailUpdateDto> envelope = KafkaEnvelope.<ItemDetailUpdateDto>builder()
                .type("ITEM_DETAIL")
                .payload(dto)
                .build();

        kafkaTemplate.send(itemDetailTopic, envelope);
    }

    public void syncItemDetailStockMinus(ItemDetail entity, Integer quantity) {
        ItemDetailUpdateDto dto = ItemDetailUpdateDto.toDtoMinus(entity, quantity);

        KafkaEnvelope<ItemDetailUpdateDto> envelope = KafkaEnvelope.<ItemDetailUpdateDto>builder()
                .type("ITEM_DETAIL")
                .payload(dto)
                .build();

        kafkaTemplate.send(itemDetailTopic, envelope);
    }

    public void syncDirectItemDetailStock(Store directStore, Integer itemDetailId, Integer quantity) {
        ItemDetailUpdateDto dto = ItemDetailUpdateDto.builder()
                .id(itemDetailId)
                .stock(quantity)
                .build();

        KafkaEnvelope<ItemDetailUpdateDto> envelope = KafkaEnvelope.<ItemDetailUpdateDto>builder()
                .type("ITEM_DETAIL")
                .payload(dto)
                .build();

        kafkaTemplate.send(directStore.getStoreNumber(), envelope);
    }
}
