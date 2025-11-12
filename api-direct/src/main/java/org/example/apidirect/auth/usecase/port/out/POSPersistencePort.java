package org.example.apidirect.auth.usecase.port.out;

import org.example.apidirect.auth.domain.POS;

import java.util.Optional;

public interface POSPersistencePort {
    POS save(POS pos);
    Optional<POS> findByPosCode(String posCode);
    Optional<POS> findByMemberId(Integer memberId);
    Optional<POS> findById(Integer id);
}
