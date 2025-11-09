package org.example.apidirect.item.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.domain.StoreItem;
import org.example.apidirect.item.usecase.port.in.SyncItemUseCase;
import org.example.apidirect.item.usecase.port.in.command.SyncItemCommand;
import org.example.apidirect.item.usecase.port.out.ItemPersistencePort;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class SyncItemService implements SyncItemUseCase {

    private final ItemPersistencePort itemPersistencePort;

    @Override
    public void syncItem(SyncItemCommand command) {

        // Command → Domain
        StoreItem item = command.toDomain();

        // 저장 (upsert)
        itemPersistencePort.save(item);

    }
}
