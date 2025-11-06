package com.example.apiauth.usecase.port.out.usecase;

public interface MemberQueryUseCase {
    Integer getMemberIdByEmail(String email);
    Integer getStoreIdByEmail(String email);
    Integer getPosIdByEmail(String email);
    Integer getDriverIdByEmail(String email);

}
