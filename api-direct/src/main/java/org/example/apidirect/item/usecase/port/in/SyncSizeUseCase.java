package org.example.apidirect.item.usecase.port.in;

import org.example.apidirect.item.usecase.port.in.command.SyncSizeCommand;

import java.util.List;

public interface SyncSizeUseCase {
    void syncSizes(List<SyncSizeCommand> commands);
}
