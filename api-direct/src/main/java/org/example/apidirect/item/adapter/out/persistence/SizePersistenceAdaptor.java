package org.example.apidirect.item.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.item.adapter.out.entity.SizeEntity;
import org.example.apidirect.item.adapter.out.mapper.SizeMapper;
import org.example.apidirect.item.domain.Size;
import org.example.apidirect.item.usecase.port.out.SizePersistencePort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class SizePersistenceAdaptor implements SizePersistencePort {

    private final SizeRepository sizeRepository;

    @Override
    @Transactional
    public void saveAll(List<Size> sizes) {
        List<SizeEntity> entities = sizes.stream()
                .map(SizeMapper::toEntity)
                .collect(Collectors.toList());

        entities.forEach(sizeRepository::upsert);
    }

    @Override
    public Optional<Size> findById(Integer id) {
        return sizeRepository.findById(id)
                .map(SizeMapper::toDomain);
    }

    @Override
    public List<Size> findAll() {
        return sizeRepository.findAll().stream()
                .map(SizeMapper::toDomain)
                .collect(Collectors.toList());
    }
}
