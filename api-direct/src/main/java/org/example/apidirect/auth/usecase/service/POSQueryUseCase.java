package org.example.apidirect.auth.usecase.service;

import MeshX.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.example.apidirect.auth.domain.POS;
import org.example.apidirect.auth.usecase.port.in.POSQueryPort;
import org.example.apidirect.auth.usecase.port.out.POSPersistencePort;
import org.example.apidirect.payment.common.PaymentException;
import org.example.apidirect.payment.common.PaymentExceptionType;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class POSQueryUseCase implements POSQueryPort {

    private final POSPersistencePort posPersistencePort;

    @Override
    public POS findByMemberId(Integer memberId) {
        return posPersistencePort.findByMemberId(memberId)
                .orElseThrow(() -> new PaymentException(PaymentExceptionType.POS_NOT_FOUND));
    }

    @Override
    public POS findByPosCode(String posCode) {
        return posPersistencePort.findByPosCode(posCode)
                .orElseThrow(() -> new PaymentException(PaymentExceptionType.POS_NOT_FOUND));
    }
}
