package org.example.apidirect.auth.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.auth.domain.Store;
import org.example.apidirect.auth.usecase.port.in.StoreQueryPort;
import org.example.apidirect.auth.usecase.port.out.StorePersistencePort;
import org.example.apidirect.payment.common.PaymentException;
import org.example.apidirect.payment.common.PaymentExceptionType;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreQueryUseCase implements StoreQueryPort {

    private final StorePersistencePort storePersistencePort;

    @Override
    public Store findById(Integer id) {
        return storePersistencePort.findById(id)
                .orElseThrow(() -> new PaymentException(PaymentExceptionType.STORE_NOT_FOUND));
    }
}
