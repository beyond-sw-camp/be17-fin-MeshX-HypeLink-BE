package org.example.apidirect.auth.adapter.out.persistence;

import MeshX.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.auth.adapter.out.mapper.POSMapper;
import org.example.apidirect.auth.domain.POS;
import org.example.apidirect.auth.usecase.port.out.POSPersistencePort;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class POSPersistenceAdaptor implements POSPersistencePort {

    private final POSRepository posRepository;

    @Override
    public POS save(POS pos) {
        var entity = POSMapper.toEntity(pos);
        var saved = posRepository.save(entity);
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
