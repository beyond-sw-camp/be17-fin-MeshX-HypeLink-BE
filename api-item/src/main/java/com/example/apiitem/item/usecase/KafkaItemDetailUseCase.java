package com.example.apiitem.item.usecase;

import MeshX.common.UseCase;
import com.example.apiitem.item.domain.ItemDetail;
import com.example.apiitem.item.usecase.port.in.KafkaItemDetailInPort;
import com.example.apiitem.item.usecase.port.in.request.kafka.ItemDetailUpdateCommand;
import com.example.apiitem.item.usecase.port.out.ItemDetailPersistencePort;
import com.example.apiitem.util.ItemDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
}
