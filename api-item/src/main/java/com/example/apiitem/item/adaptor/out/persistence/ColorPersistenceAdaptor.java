package com.example.apiitem.item.adaptor.out.persistence;

import MeshX.common.PersistenceAdapter;
import MeshX.common.exception.BaseException;
import com.example.apiitem.item.adaptor.out.jpa.ColorEntity;
import com.example.apiitem.item.adaptor.out.jpa.ColorRepository;
import com.example.apiitem.item.domain.Color;
import com.example.apiitem.item.usecase.port.out.ColorPersistencePort;
import com.example.apiitem.util.ColorMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class ColorPersistenceAdaptor implements ColorPersistencePort {
    private final ColorRepository colorRepository;

    @Override
    public void saveAllWithId(List<Color> colors) {
        List<ColorEntity> list = colors.stream().map(ColorMapper::toEntity).toList();
        list.forEach(colorRepository::upsert);
    }

    @Override
    public List<Color> findAll() {
        List<ColorEntity> all = colorRepository.findAll();
        if(all.isEmpty()) {
            throw new BaseException(null);
        }

        return all.stream()
                .map(ColorMapper::toDomain)
                .toList();
    }
}
