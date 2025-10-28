package MeshX.HypeLink.common.exception;

import MeshX.HypeLink.auth.exception.TokenException;
import MeshX.HypeLink.utils.geocode.exception.GeocodingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> baseExceptionHandler(BaseException e) {
        String errorClassName = e.getClass().getSimpleName();
        String title = e.getExceptionType().title();
        String errorMessage = e.getExceptionType().message();

        log.error("[{}] >>{} : {}", errorClassName, title, errorMessage);
        return ResponseEntity.ok(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Map<String, Object>> handleTokenException(TokenException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(GeocodingException.class)
    public ResponseEntity<Map<String, Object>> handleGeocodingException(GeocodingException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", "주소 변환에 실패했습니다. 주소를 확인해주세요."); // 클라이언트에게 보여줄 메시지

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}