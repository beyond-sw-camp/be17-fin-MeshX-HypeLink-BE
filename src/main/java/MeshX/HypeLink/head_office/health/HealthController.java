package MeshX.HypeLink.head_office.health;

import MeshX.HypeLink.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<BaseResponse<String>> health() {
        return ResponseEntity.status(200).body(BaseResponse.of("OK"));
    }

    @GetMapping("/test")
    public ResponseEntity<BaseResponse<String>> test() {
        return ResponseEntity.status(200).body(BaseResponse.of("OK"));
    }
}
