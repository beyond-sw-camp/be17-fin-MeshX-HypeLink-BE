package com.example.apiauth.adapter.in.web;

import MeshX.common.BaseResponse;
import MeshX.common.WebAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Tag(name = "헬스체크", description = "서비스 상태 확인 API")
@WebAdapter
@RequestMapping("/api/health")
public class HealthController {

    @Operation(summary = "헬스체크", description = "서비스 상태를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "서비스 정상 작동",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "서비스 오류", content = @Content)
    })
    @GetMapping("/health")
    public ResponseEntity<BaseResponse<Map<String, String>>> health() {
        Map<String, String> status = Map.of("status", "UP");
        return ResponseEntity.ok().body(BaseResponse.of(status));
    }

    @Operation(summary = "테스트", description = "서비스 테스트를 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "테스트 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "테스트 실패", content = @Content)
    })
    @GetMapping("/test")
    public ResponseEntity<BaseResponse<Map<String, String>>> test() {
        Map<String, String> status = Map.of("status", "UP");
        return ResponseEntity.ok().body(BaseResponse.of(status));
    }
}
