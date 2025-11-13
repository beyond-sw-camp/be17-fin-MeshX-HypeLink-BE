package com.example.apiauth.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import com.example.apiauth.adapter.in.web.dto.*;
import com.example.apiauth.usecase.port.out.usecase.MemberQueryUseCase;
import com.example.apiclients.annotation.GetMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import com.example.apiclients.annotation.RequireRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@WebAdapter
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberQueryUseCase memberQueryUseCase;

    // member중 POS 제외
    @GetMapping("/member/list")
    public ResponseEntity<BaseResponse<List<MemberListNotPosResDto>>> list() {
        List<MemberListNotPosResDto> result = memberQueryUseCase.memberlistNotPos();
        return ResponseEntity.ok(BaseResponse.of(result));
    }


    @GetMapping("/driver/list")
    public ResponseEntity<BaseResponse<List<DriverListResDto>>> driverList() {
        List<DriverListResDto> result = memberQueryUseCase.dirverList();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/storepos/list")
    public ResponseEntity<BaseResponse<List<StoreWithPosResDto>>> storeWithPosList() {
        List<StoreWithPosResDto> result = memberQueryUseCase.storeWithPosList();

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/store/list")
    public ResponseEntity<BaseResponse<Page<StoreListResDto>>> storeList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "") String keyWord,
            @RequestParam(defaultValue = "all") String status) {
        Page<StoreListResDto> result = memberQueryUseCase.storeList(pageable, keyWord, status);

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/mystore/read")
    public ResponseEntity<BaseResponse<StoreWithPosResDto>> readMyStore(@GetMemberId Integer memberId) {
        StoreWithPosResDto result = memberQueryUseCase.readMyStore(memberId);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/otherstore/read/{id}")
    public ResponseEntity<BaseResponse<StoreWithPosResDto>> readOtherStore(@PathVariable Integer id) {
        StoreWithPosResDto result = memberQueryUseCase.readOtherStroe(id);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/storeinfo/read/{id}")
    public ResponseEntity<BaseResponse<StoreInfoResDto>> readStoreInfo(@PathVariable Integer id) {
        StoreInfoResDto result = memberQueryUseCase.readStoreInfo(id);
        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @GetMapping("/user/read/{id}")
    public ResponseEntity<BaseResponse<UserReadResDto>> readUser(@PathVariable Integer id) {
        UserReadResDto result = memberQueryUseCase.userRead(id);

        return ResponseEntity.ok(BaseResponse.of(result));
    }

    @RequireRole({"ADMIN", "MANAGER"})
    @GetMapping("/store/list/address")
    public ResponseEntity<BaseResponse<List<StoreAddInfoResDto>>> getStoreAddress() {
        List<StoreAddInfoResDto> result = memberQueryUseCase.getStoreAddress();
        return ResponseEntity.ok(BaseResponse.of(result));
    }

}
