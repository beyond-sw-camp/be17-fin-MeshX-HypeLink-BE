package org.example.apidirect.item.usecase.port.out;

import org.example.apidirect.item.domain.Size;

import java.util.List;

public interface SizePersistencePort {
    void saveAll(List<Size> sizes);
}
