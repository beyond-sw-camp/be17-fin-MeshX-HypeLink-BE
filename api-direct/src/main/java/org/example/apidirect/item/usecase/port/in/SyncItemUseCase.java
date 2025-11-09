package org.example.apidirect.item.usecase.port.in;

import org.example.apidirect.item.usecase.port.in.command.SyncItemCommand;

public interface SyncItemUseCase {
    void syncItem(SyncItemCommand command);
}
