package org.example.apidirect.item.usecase.port.in;

import org.example.apidirect.item.usecase.port.in.command.SyncColorCommand;

import java.util.List;

public interface SyncColorUseCase {
    void syncColors(List<SyncColorCommand> commands);
}
