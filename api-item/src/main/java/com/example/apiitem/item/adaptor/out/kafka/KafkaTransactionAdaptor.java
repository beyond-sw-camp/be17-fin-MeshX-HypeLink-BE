package com.example.apiitem.item.adaptor.out.kafka;

import MeshX.common.EventOutAdapter;
import com.example.apiitem.item.adaptor.out.feign.dto.SaveItemDetailReq;
import com.example.apiitem.item.adaptor.out.feign.dto.SaveItemDetailsReq;
import com.example.apiitem.item.adaptor.out.feign.dto.SaveItemReq;
import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemDetail;
import com.example.apiitem.item.usecase.port.in.request.CreateItemCommand;
import com.example.apiitem.item.usecase.port.in.request.kafka.KafkaEnvelope;
import com.example.apiitem.item.usecase.port.out.KafkaItemOutPort;
import com.example.apiitem.util.ItemDetailMapper;
import com.example.apiitem.util.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@EventOutAdapter
@RequiredArgsConstructor
public class KafkaTransactionAdaptor implements KafkaItemOutPort {
    @Value("${kafka.topic.transaction.item.save}")
    private String itemSaveTopic;
    @Value("${kafka.topic.transaction.itemDetail.save}")
    private String itemDetailSaveTopic;

    private final KafkaTemplate<String, KafkaEnvelope> kafkaTemplate;

    @Override
    public String saveItem(Item domain, List<ItemDetail> itemDetails) {
        List<SaveItemDetailReq> itemDetailReqs = itemDetails.stream()
                .map(ItemDetailMapper::toFeignDto)
                .toList();
        SaveItemReq dto = ItemMapper.toFeignDto(domain, itemDetailReqs);

        KafkaEnvelope<SaveItemReq> envelope = KafkaEnvelope.<SaveItemReq>builder()
                .type("ITEM")
                .payload(dto)
                .build();

        kafkaTemplate.send(itemSaveTopic, envelope);
        return "save";
    }

    @Override
    public String saveItemDetail(Item item, List<ItemDetail> itemDetails) {
        List<SaveItemDetailReq> itemDetailReqs = itemDetails.stream()
                .map(ItemDetailMapper::toFeignDto)
                .toList();
        SaveItemDetailsReq dto = ItemDetailMapper.toFeignDto(item, itemDetailReqs);

        KafkaEnvelope<SaveItemDetailsReq> envelope = KafkaEnvelope.<SaveItemDetailsReq>builder()
                .type("ITEM_DETAIL")
                .payload(dto)
                .build();

        kafkaTemplate.send(itemDetailSaveTopic, envelope);
        return "save";
    }
}
