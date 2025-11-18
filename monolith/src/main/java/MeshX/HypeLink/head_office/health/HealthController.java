package MeshX.HypeLink.head_office.health;

import MeshX.HypeLink.common.BaseResponse;
import MeshX.HypeLink.head_office.health.constants.HealthSwaggerConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "헬스 체크", description = "시스템 상태 확인 및 헬스 체크 API")
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Operation(summary = "헬스 체크", description = "시스템의 현재 상태를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시스템 정상 작동 중",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = HealthSwaggerConstants.HEALTH_RES_EXAMPLE)))
    })
    @GetMapping("/health")
    public ResponseEntity<BaseResponse<Map<String, String>>> health() {
        Map<String, String> status = Map.of("status", "UP");
        return ResponseEntity.status(200).body(BaseResponse.of(status));
    }

    @Operation(summary = "테스트 엔드포인트", description = "API 연결 테스트를 위한 엔드포인트입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API 연결 정상",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseResponse.class),
                            examples = @ExampleObject(value = HealthSwaggerConstants.TEST_RES_EXAMPLE)))
    })
    @GetMapping("/test")
    public ResponseEntity<BaseResponse<Map<String, String>>> test() {
        Map<String, String> status = Map.of("status", "UP");
        return ResponseEntity.status(200).body(BaseResponse.of(status));
    }
}

