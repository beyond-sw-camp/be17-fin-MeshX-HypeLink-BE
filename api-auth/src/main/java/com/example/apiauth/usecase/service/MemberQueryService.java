package com.example.apiauth.usecase.service;

import MeshX.common.UseCase;
import com.example.apiauth.common.exception.AuthException;
import com.example.apiauth.domain.model.Member;
import com.example.apiauth.usecase.port.out.persistence.MemberPort;
import com.example.apiauth.usecase.port.out.persistence.MemberQueryUseCase;
import lombok.RequiredArgsConstructor;

import static com.example.apiauth.common.exception.AuthExceptionMessage.USER_NAME_NOT_FOUND;

@UseCase
@RequiredArgsConstructor
public class MemberQueryService implements MemberQueryUseCase {
    private final MemberPort memberPort;

    @Override
    public Integer getMemberIdByEmail(String email) {
        Member member = memberPort.findByEmail(email)
                .orElseThrow(() -> new AuthException(USER_NAME_NOT_FOUND));

        return member.getId();
    }


}
