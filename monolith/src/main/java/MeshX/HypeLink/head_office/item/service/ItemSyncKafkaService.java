package MeshX.HypeLink.head_office.item.service;

import MeshX.HypeLink.head_office.item.model.entity.Category;
import MeshX.HypeLink.head_office.item.model.entity.Color;
import MeshX.HypeLink.head_office.item.model.entity.Item;
import MeshX.HypeLink.head_office.item.model.entity.Size;
import MeshX.HypeLink.head_office.item.repository.CategoryJpaRepositoryVerify;
import MeshX.HypeLink.head_office.item.repository.ColorJpaRepositoryVerify;
import MeshX.HypeLink.head_office.item.repository.ItemJpaRepositoryVerify;
import MeshX.HypeLink.head_office.item.repository.SizeJpaRepositoryVerify;
import MeshX.HypeLink.head_office.item.service.kafka.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSyncKafkaService {
    private final KafkaTemplate<String, KafkaEnvelope> kafkaTemplate;

    private final ItemJpaRepositoryVerify itemRepository;
    private final CategoryJpaRepositoryVerify categoryRepository;
    private final SizeJpaRepositoryVerify sizeRepository;
    private final ColorJpaRepositoryVerify colorRepository;

    @Transactional(readOnly = true)
    @Scheduled(cron = "0 43 * * * *") // 매시 33분
    public void sync() {
        syncCategoryList();
        syncColorList();
        syncSizeList();
        syncItemList();
    }

    public void syncCategoryList() {
        List<Category> all = categoryRepository.findAll();
        KafkaCategoryList dto = KafkaCategoryList.toDto(all);
        KafkaEnvelope<KafkaCategoryList> envelope = KafkaEnvelope.<KafkaCategoryList>builder()
                .type("CATEGORY")
                .payload(dto)
                .build();
        kafkaTemplate.send("category.sync.1", envelope);
    }

    public void syncSizeList() {
        List<Size> all = sizeRepository.findAll();
        KafkaSizeList dto = KafkaSizeList.toDto(all);
        KafkaEnvelope<KafkaSizeList> envelope = KafkaEnvelope.<KafkaSizeList>builder()
                .type("SIZE")
                .payload(dto)
                .build();
        kafkaTemplate.send("size.sync.1", envelope);
    }

    public void syncColorList() {
        List<Color> all = colorRepository.findAll();
        KafkaColorList dto = KafkaColorList.toDto(all);
        KafkaEnvelope<KafkaColorList> envelope = KafkaEnvelope.<KafkaColorList>builder()
                .type("COLOR")
                .payload(dto)
                .build();
        kafkaTemplate.send("color.sync.1", envelope);
    }

    public void syncItemList() {
        int page = 0;
        int size = 20; // 페이지당 20개씩 전송 (적절히 조절 가능)
        Page<Item> itemPage;

        do {
            Pageable pageable = PageRequest.of(page, size);
            itemPage = itemRepository.findItemsWithRelations(pageable);

            List<Item> items = itemPage.getContent();
            if (items.isEmpty()) break;

            for (Item entity : items) {
                syncItem(entity);
            }

            // 다음 페이지로
            page++;
        } while (itemPage.hasNext());

        log.info("Kafka item sync completed for all pages.");
    }

    public void syncItem(Item entity) {
        KafkaItemDto dto = KafkaItemDto.toDto(entity);
        KafkaEnvelope<KafkaItemDto> envelope = KafkaEnvelope.<KafkaItemDto>builder()
                .type("ITEM")
                .payload(dto)
                .build();
        kafkaTemplate.send("items.sync.1",envelope)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka send failed for itemCode: {} / error={}",
                                dto.getItemCode(), ex.getMessage());
                    } else {
                        log.info("Sent item={} offset={}",
                                dto.getItemCode(), result.getRecordMetadata().offset());
                    }
                });
    }
}
