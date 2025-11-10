package org.example.apidirect.item.usecase.port.in;

import org.example.apidirect.item.usecase.port.in.command.SyncCategoryCommand;

public interface SyncCategoryUseCase {
    void syncCategories(SyncCategoryCommand command);
}
