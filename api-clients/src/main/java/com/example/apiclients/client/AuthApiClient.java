package com.example.apiclients.client;

import com.example.apiclients.dto.ApiRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "api-auth")
public interface AuthApiClient {

    @GetMapping("/internal/auth/member")
    ResponseEntity<ApiRes<Integer>> getMemberIdByEmail(@RequestParam("email") String email);

}
