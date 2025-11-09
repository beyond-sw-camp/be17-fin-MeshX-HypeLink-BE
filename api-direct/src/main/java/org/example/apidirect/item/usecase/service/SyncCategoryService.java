package org.example.apidirect.item.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.usecase.port.in.SyncCategoryUseCase;
import org.example.apidirect.item.usecase.port.out.CategoryPersistencePort;

import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class SyncCategoryService implements SyncCategoryUseCase {

    private final CategoryPersistencePort categoryPersistencePort;

    @Override
    public void syncCategories(List<String> categories) {

        categoryPersistencePort.saveAll(categories);

    }
}
