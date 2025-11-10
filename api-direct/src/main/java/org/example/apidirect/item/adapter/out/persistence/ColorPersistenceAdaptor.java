package org.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.item.adapter.out.entity.ColorEntity;
import org.example.apidirect.item.adapter.out.mapper.ColorMapper;
import org.example.apidirect.item.domain.Color;
import org.example.apidirect.item.usecase.port.out.ColorPersistencePort;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class ColorPersistenceAdaptor implements ColorPersistencePort {

    private final ColorRepository colorRepository;

    @Override
    public void saveAll(List<Color> colors) {
        List<ColorEntity> entities = colors.stream()
                .map(ColorMapper::toEntity)
                .collect(Collectors.toList());

        colorRepository.saveAll(entities);
    }
}
