package org.example.apidirect.item.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.item.usecase.port.in.SyncCategoryUseCase;
import org.example.apidirect.item.usecase.port.in.command.SyncCategoryCommand;
import org.example.apidirect.item.usecase.port.out.CategoryPersistencePort;

@UseCase
@RequiredArgsConstructor
public class SyncCategoryService implements SyncCategoryUseCase {

    private final CategoryPersistencePort categoryPersistencePort;

    @Override
    public void syncCategories(SyncCategoryCommand command) {

        categoryPersistencePort.saveAll(command.getCategories());

    }
}
