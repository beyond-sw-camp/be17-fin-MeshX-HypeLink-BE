package org.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.item.adapter.out.entity.ColorEntity;
import org.example.apidirect.item.adapter.out.mapper.ColorMapper;
import org.example.apidirect.item.domain.Color;
import org.example.apidirect.item.usecase.port.out.ColorPersistencePort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class ColorPersistenceAdaptor implements ColorPersistencePort {

    private final ColorRepository colorRepository;

    @Override
    @Transactional
    public void saveAll(List<Color> colors) {
        List<ColorEntity> entities = colors.stream()
                .map(ColorMapper::toEntity)
                .collect(Collectors.toList());

        entities.forEach(colorRepository::upsert);
    }

    @Override
    public Optional<Color> findById(Integer id) {
        return colorRepository.findById(id)
                .map(ColorMapper::toDomain);
    }

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll().stream()
                .map(ColorMapper::toDomain)
                .collect(Collectors.toList());
    }
}
