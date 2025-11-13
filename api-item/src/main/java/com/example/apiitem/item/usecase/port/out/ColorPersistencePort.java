package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Color;

import java.util.List;

public interface ColorPersistencePort {
    void saveAllWithId(List<Color> colors);

    List<Color> findAll();
}
