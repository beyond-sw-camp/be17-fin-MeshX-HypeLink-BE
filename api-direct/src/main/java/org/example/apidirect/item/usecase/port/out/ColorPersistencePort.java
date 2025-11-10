package org.example.apidirect.item.usecase.port.out;

import org.example.apidirect.item.domain.Color;

import java.util.List;

public interface ColorPersistencePort {
    void saveAll(List<Color> colors);
}
