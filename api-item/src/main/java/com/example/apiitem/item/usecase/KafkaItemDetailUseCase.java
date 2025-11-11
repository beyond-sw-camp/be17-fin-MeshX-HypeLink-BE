package com.example.apiitem.item.usecase;

import MeshX.common.UseCase;
import com.example.apiitem.item.adaptor.out.feign.dto.SaveItemDetailsReq;
import com.example.apiitem.item.domain.Item;
import com.example.apiitem.item.domain.ItemDetail;
import com.example.apiitem.item.usecase.port.in.KafkaItemDetailInPort;
import com.example.apiitem.item.usecase.port.in.request.kafka.ItemDetailUpdateCommand;
import com.example.apiitem.item.usecase.port.out.ItemDetailPersistencePort;
import com.example.apiitem.util.ItemDetailMapper;
import com.example.apiitem.util.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional
@RequiredArgsConstructor
public class KafkaItemDetailUseCase implements KafkaItemDetailInPort {
    private final ItemDetailPersistencePort itemDetailPersistencePort;

    @Override
    public void updateItemDetail(ItemDetailUpdateCommand command) {
        ItemDetail domain = ItemDetailMapper.toDomain(command);

        itemDetailPersistencePort.updateStock(domain);
    }

    @Override
    public void rollback(SaveItemDetailsReq command) {
        List<ItemDetail> itemDetails = command.getDetails().stream()
                .map(ItemDetailMapper::toDomain)
                .toList();
        Item domain = ItemMapper.toDomain(command.getItemId());

        itemDetailPersistencePort.rollback(domain, itemDetails);
    }
}
