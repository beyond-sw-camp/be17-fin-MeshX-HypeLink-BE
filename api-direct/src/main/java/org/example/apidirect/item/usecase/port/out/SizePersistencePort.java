package org.example.apidirect.item.usecase.port.out;

import org.example.apidirect.item.domain.Size;

import java.util.List;
import java.util.Optional;

public interface SizePersistencePort {
    void saveAll(List<Size> sizes);

    Optional<Size> findById(Integer id);

    List<Size> findAll();
}
