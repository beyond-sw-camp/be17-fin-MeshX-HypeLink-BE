package com.example.apiauth.usecase.port.out.usecase;

import com.example.apiauth.adapter.in.web.dto.StoreInfoResDto;
import com.example.apiauth.adapter.in.web.dto.StoreStateReqDto;
import com.example.apiauth.adapter.in.web.dto.UserReadResDto;

public interface MemberCommandUseCase {

    void updateStoreInfo(Integer id, StoreInfoResDto dto);

    void storeStateUpdate(Integer id, StoreStateReqDto dto);

    void updateUser(Integer id, UserReadResDto dto);

    void deleteUser(Integer id);

    void deleteStore(Integer id);

    void deletePos(Integer id);

    void deleteDriver(Integer id);
}
