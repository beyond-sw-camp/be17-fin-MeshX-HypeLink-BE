package com.example.apidirect.auth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import com.example.apidirect.auth.adapter.out.entity.POSEntity;
import com.example.apidirect.auth.adapter.out.mapper.POSMapper;
import com.example.apidirect.auth.domain.POS;
import com.example.apidirect.auth.usecase.port.out.POSPersistencePort;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class POSPersistenceAdaptor implements POSPersistencePort {

    private final POSRepository posRepository;

    @Override
    public POS save(POS pos) {
        POSEntity entity = POSMapper.toEntity(pos);
        POSEntity saved = posRepository.save(entity);
        return POSMapper.toDomain(saved);
    }

    @Override
    public Optional<POS> findByPosCode(String posCode) {
        return posRepository.findByPosCode(posCode)
                .map(POSMapper::toDomain);
    }

    @Override
    public Optional<POS> findByMemberId(Integer memberId) {
        return posRepository.findByMemberId(memberId)
                .map(POSMapper::toDomain);
    }

    @Override
    public Optional<POS> findById(Integer id) {
        return posRepository.findById(id)
                .map(POSMapper::toDomain);
    }
}
