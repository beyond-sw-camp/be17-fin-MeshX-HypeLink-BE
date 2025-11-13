package com.example.apiauth.usecase.port.out.usecase;

import com.example.apiauth.adapter.in.web.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberQueryUseCase {
    Integer getMemberIdByEmail(String email);
    Integer getStoreIdByEmail(String email);
    Integer getPosIdByEmail(String email);
    Integer getDriverIdByEmail(String email);


    List<MemberListNotPosResDto> memberlistNotPos();

    List<DriverListResDto> dirverList();

    List<StoreWithPosResDto> storeWithPosList();

    Page<StoreListResDto> storeList(Pageable pageable, String keyWord, String status);

    StoreWithPosResDto readMyStore(Integer memberId);

    StoreWithPosResDto readOtherStroe(Integer id);

    StoreInfoResDto readStoreInfo(Integer id);

    UserReadResDto userRead(Integer id);

    List<StoreAddInfoResDto> getStoreAddress();
}
