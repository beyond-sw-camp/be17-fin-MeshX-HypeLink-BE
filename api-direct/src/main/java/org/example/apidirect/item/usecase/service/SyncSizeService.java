package org.example.apidirect.item.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.domain.Size;
import org.example.apidirect.item.usecase.port.in.SyncSizeUseCase;
import org.example.apidirect.item.usecase.port.in.command.SyncSizeCommand;
import org.example.apidirect.item.usecase.port.out.SizePersistencePort;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class SyncSizeService implements SyncSizeUseCase {

    private final SizePersistencePort sizePersistencePort;

    @Override
    public void syncSizes(List<SyncSizeCommand> commands) {
        List<Size> sizes = commands.stream()
                .map(SyncSizeCommand::toDomain)
                .collect(Collectors.toList());

        sizePersistencePort.saveAll(sizes);
    }
}
