package org.example.apidirect.auth.usecase.port.in;

import org.example.apidirect.auth.domain.POS;

public interface POSQueryPort {
    POS findByMemberId(Integer memberId);
    POS findByPosCode(String posCode);
}
