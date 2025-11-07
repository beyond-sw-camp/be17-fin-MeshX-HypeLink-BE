package com.example.apiauth.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@WebAdapter
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<BaseResponse<Map<String, String>>> health() {
        Map<String, String> status = Map.of("status", "UP");
        return ResponseEntity.ok().body(BaseResponse.of(status));
    }

    @GetMapping("/test")
    public ResponseEntity<BaseResponse<Map<String, String>>> test() {
        Map<String, String> status = Map.of("status", "UP");
        return ResponseEntity.ok().body(BaseResponse.of(status));
    }
}
