package org.example.apidirect.item.usecase.port.out;

import org.example.apidirect.item.domain.Color;

import java.util.List;
import java.util.Optional;

public interface ColorPersistencePort {
    void saveAll(List<Color> colors);

    Optional<Color> findById(Integer id);

    List<Color> findAll();
}
