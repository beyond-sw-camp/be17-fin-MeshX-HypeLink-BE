package com.example.apiauth.usecase.port.out.persistence;

public interface MemberQueryUseCase {
    Integer getMemberIdByEmail(String email);
}
