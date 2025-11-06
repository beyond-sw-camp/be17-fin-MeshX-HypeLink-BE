package com.example.apiauth.usecase.service;

import MeshX.common.UseCase;
import com.example.apiauth.adapter.out.persistence.entity.MemberEntity;
import com.example.apiauth.adapter.out.persistence.mapper.MemberMapper;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.model.Driver;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.domain.model.Pos;
import com.example.apiauth.domain.model.Store;
import com.example.apiauth.usecase.port.out.persistence.DriverPort;
import com.example.apiauth.usecase.port.out.persistence.MemberPort;
import com.example.apiauth.usecase.port.out.persistence.PosPort;
import com.example.apiauth.usecase.port.out.persistence.StorePort;
import com.example.apiauth.usecase.port.out.usecase.MemberQueryUseCase;
import lombok.RequiredArgsConstructor;

import static com.example.apiauth.common.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

@UseCase
@RequiredArgsConstructor
public class MemberQueryService implements MemberQueryUseCase {

    private final MemberPort memberPort;
    private final StorePort storePort;
    private final PosPort posPort;
    private final DriverPort driverPort;

    @Override
    public Integer getMemberIdByEmail(String email) {
        Member member = memberPort.findByEmail(email);
        return member.getId();
    }
    @Override
    public Integer getStoreIdByEmail(String email) {
        Store store = storePort.findByMember_Email(email);
        return store.getId();
    }

    @Override
    public Integer getPosIdByEmail(String email) {
        Pos pos = posPort.findByMember_Email(email);
        return pos.getId();
    }

    @Override
    public Integer getDriverIdByEmail(String email) {
        Driver driver = driverPort.findByMember_Email(email);
        return driver.getId();
    }

}
