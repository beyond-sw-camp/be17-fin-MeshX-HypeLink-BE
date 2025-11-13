package com.example.apiitem.item.usecase.port.out;

import com.example.apiitem.item.domain.Size;

import java.util.List;

public interface SizePersistencePort {
    void saveAllWithId(List<Size> sizes);

    List<Size> findAll();
}
