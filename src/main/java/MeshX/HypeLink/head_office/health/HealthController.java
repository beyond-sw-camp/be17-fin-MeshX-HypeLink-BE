package MeshX.HypeLink.head_office.health;

import MeshX.HypeLink.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<BaseResponse<Map<String, String>>> health() {
        Map<String, String> status = Map.of("status", "UP");
        return ResponseEntity.status(200).body(BaseResponse.of(status));
    }

    @GetMapping("/test")
    public ResponseEntity<BaseResponse<Map<String, String>>> test() {
        Map<String, String> status = Map.of("status", "UP");
        return ResponseEntity.status(200).body(BaseResponse.of(status));
    }
}
