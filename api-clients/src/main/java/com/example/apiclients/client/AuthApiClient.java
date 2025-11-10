package com.example.apiclients.client;

import MeshX.common.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "api-auth")
public interface AuthApiClient {

    @GetMapping("/internal/auth/member")
    BaseResponse<Integer> getMemberIdByEmail(@RequestParam("email") String email);

    @GetMapping("/internal/auth/store")
    BaseResponse<Integer> getStoreIdByEmail(@RequestParam("email") String email);

    @GetMapping("/internal/auth/pos")
    BaseResponse<Integer> getPosIdByEmail(@RequestParam("email") String email);

    @GetMapping("/internal/auth/driver")
    BaseResponse<Integer> getDriverIdByEmail(@RequestParam("email") String email);

}
