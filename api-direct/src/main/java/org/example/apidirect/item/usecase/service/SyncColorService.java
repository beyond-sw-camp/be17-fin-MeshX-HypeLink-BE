package org.example.apidirect.item.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apidirect.item.domain.Color;
import org.example.apidirect.item.usecase.port.in.SyncColorUseCase;
import org.example.apidirect.item.usecase.port.in.command.SyncColorCommand;
import org.example.apidirect.item.usecase.port.out.ColorPersistencePort;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class SyncColorService implements SyncColorUseCase {

    private final ColorPersistencePort colorPersistencePort;

    @Override
    public void syncColors(List<SyncColorCommand> commands) {
        List<Color> colors = commands.stream()
                .map(SyncColorCommand::toDomain)
                .collect(Collectors.toList());

        colorPersistencePort.saveAll(colors);
    }
}
