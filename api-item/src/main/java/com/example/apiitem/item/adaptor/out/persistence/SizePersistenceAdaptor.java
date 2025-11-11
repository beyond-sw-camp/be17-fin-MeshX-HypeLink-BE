package com.example.apiitem.item.adaptor.out.persistence;

import MeshX.common.PersistenceAdapter;
import com.example.apiitem.item.adaptor.out.jpa.SizeEntity;
import com.example.apiitem.item.adaptor.out.jpa.SizeRepository;
import com.example.apiitem.item.domain.Size;
import com.example.apiitem.item.usecase.port.out.SizePersistencePort;
import com.example.apiitem.util.SizeMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class SizePersistenceAdaptor implements SizePersistencePort {
    private final SizeRepository sizeRepository;

    @Override
    public void saveAllWithId(List<Size> sizes) {
        List<SizeEntity> list = sizes.stream().map(SizeMapper::toEntity).toList();
        list.forEach(sizeRepository::upsert);
    }
}
